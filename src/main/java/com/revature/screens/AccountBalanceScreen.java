package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.BankAccount;

public class AccountBalanceScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccount ba;

	public AccountBalanceScreen() {
		super();
		AccountTypeSelectScreen accountTypeSelectScreen = new AccountTypeSelectScreen("view account balance of");
		ba = accountTypeSelectScreen.getBankAccount();
	}

	@Override
	public Screen start() {
		System.out.println("Your current bank account balance is " + ba.getBalanceString() + ".");
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}