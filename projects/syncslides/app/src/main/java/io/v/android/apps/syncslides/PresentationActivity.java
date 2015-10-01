// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.android.apps.syncslides;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PresentationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        if (savedInstanceState == null) {
            SlideListFragment slideList = new SlideListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, slideList).commit();
        }
    }

    /**
     * Swap out the current fragment for a NavigateFragment.
     * @param slideNum the slide to show
     */
    public void jumpToSlide(int slideNum) {
        // TODO(kash): Actually navigate to the right slide.  Need vcl/16118 for that.
        NavigateFragment fragment = new NavigateFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }
}
