// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.android.apps.syncslides;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Signs in the user into one of his Gmail accounts.
 */
public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";

    public static final String PREF_USER_ACCOUNT_NAME = "user_account";
    public static final String PREF_USER_PROFILE_JSON = "user_profile";

    private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private static final int REQUEST_CODE_FETCH_USER_PROFILE_APPROVAL = 1001;

    private static final String OAUTH_PROFILE = "email";
    private static final String OAUTH_SCOPE = "oauth2:" + OAUTH_PROFILE;
    private static final String OAUTH_USERINFO_URL =
            "https://www.googleapis.com/oauth2/v2/userinfo";

    private SharedPreferences mPrefs;
    private String mAccountName;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mAccountName = mPrefs.getString(SignInActivity.PREF_USER_ACCOUNT_NAME, "");
        mProgressDialog = new ProgressDialog(this);
        if (mAccountName.isEmpty()) {
            mProgressDialog.setMessage("Signing in...");
            mProgressDialog.show();
            pickAccount();
        } else {
            finishActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_ACCOUNT: {
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this, "Must pick account", Toast.LENGTH_LONG).show();
                    pickAccount();
                    break;
                }
                pickAccountDone(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
                break;
            }
            case REQUEST_CODE_FETCH_USER_PROFILE_APPROVAL:
                if (resultCode != RESULT_OK) {
                    Log.e(TAG, "User didn't approve oauth2 request");
                    break;
                }
                fetchUserProfile();
                break;
        }
    }

    private void pickAccount() {
        Intent chooseIntent = AccountManager.newChooseAccountIntent(
                null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(chooseIntent, REQUEST_CODE_PICK_ACCOUNT);
    }

    private void pickAccountDone(String accountName) {
        mAccountName = accountName;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_USER_ACCOUNT_NAME, accountName);
        editor.commit();

        fetchUserProfile();
    }

    private void fetchUserProfile() {
        AccountManager manager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = manager.getAccountsByType("com.google");
        Account account = null;
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].name.equals(mAccountName)) {
                account = accounts[i];
                break;
            }
        }
        if (account == null) {
            Log.e(TAG, "Couldn't find Google account with name: " + mAccountName);
            pickAccount();
            return;
        }
        manager.getAuthToken(account,
                OAUTH_SCOPE,
                new Bundle(),
                false,
                new OnTokenAcquired(),
                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Log.e(TAG, "Error getting auth token: " + msg.toString());
                        fetchUserProfileDone(null);
                        return true;
                    }
                }));
    }

    private void fetchUserProfileDone(JSONObject userProfile) {
        if (userProfile != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_USER_PROFILE_JSON, userProfile.toString());
            editor.commit();
        }

        finishActivity();
    }


    private void finishActivity() {
        mProgressDialog.dismiss();
        startActivity(new Intent(this, DeckChooserActivity.class));
        finish();
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try {
                Bundle bundle = result.getResult();
                Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
                if (launch != null) {  // Needs user approval.
                    // NOTE(spetrovic): The returned intent has the wrong flag value
                    // FLAG_ACTIVITY_NEW_TASK set, which results in the launched intent replying
                    // immediately with RESULT_CANCELED.  Hence, we clear the flag here.
                    launch.setFlags(0);
                    startActivityForResult(launch, REQUEST_CODE_FETCH_USER_PROFILE_APPROVAL);
                    return;
                }
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                new ProfileInfoFetcher().execute(token);
            } catch (AuthenticatorException e) {
                Log.e(TAG, "Couldn't authorize: " + e.getMessage());
                fetchUserProfileDone(null);
            } catch (OperationCanceledException e) {
                Log.e(TAG, "Authorization cancelled: " + e.getMessage());
                fetchUserProfileDone(null);
            } catch (IOException e) {
                Log.e(TAG, "Unexpected error: " + e.getMessage());
                fetchUserProfileDone(null);
            }
        }
    }

    private class ProfileInfoFetcher extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                URL url = new URL(OAUTH_USERINFO_URL + "?access_token=" + params[0]);
                return new JSONObject(CharStreams.toString(
                        new InputStreamReader(url.openConnection().getInputStream(),
                                Charsets.US_ASCII)));
            } catch (MalformedURLException e) {
                Log.e(TAG, "Error fetching user's profile info" + e.getMessage());
            } catch (JSONException e) {
                Log.e(TAG, "Error fetching user's profile info" + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "Error fetching user's profile info" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject userProfile) {
            fetchUserProfileDone(userProfile);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
