package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;

public class AccountBalanceScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public Screen start() {
		User currentUser = User.getCurrentUser();
		BankAccount ba = bad.findByUsername(currentUser.getUsername());
		System.out.println("Your current bank account balance is $" + ba.getBalance() + ".");
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}