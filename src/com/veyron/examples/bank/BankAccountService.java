// This file was auto-generated by the veyron vdl tool.
// Source: bank.vdl
package com.veyron.examples.bank;

import com.veyron.examples.bank.gen_impl.BankAccountServiceWrapper;
import com.veyron2.ipc.ServerContext;
import com.veyron2.ipc.VeyronException;
import com.veyron2.vdl.VeyronService;

/**
 * The BankAccount can only be accessed by blessed users
**/
@VeyronService(serviceWrapper=BankAccountServiceWrapper.class)
public interface BankAccountService { 
	// Deposit adds the amount given to this account.
	public void deposit(ServerContext context, long amount) throws VeyronException;
	// Withdraw reduces the amount given from this account.
	public void withdraw(ServerContext context, long amount) throws VeyronException;
	// Transfer moves the amount given to the receiver's account.
	public void transfer(ServerContext context, long receiver, long amount) throws VeyronException;
	// Balance returns the amount stored in this account.
	public long balance(ServerContext context) throws VeyronException;
}
