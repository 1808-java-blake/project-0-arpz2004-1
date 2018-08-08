package com.revature.screens;

import java.util.Scanner;

import com.revature.daos.BankAccountDao;

public class AccountBalanceScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public Screen start() {
		bad.findByUsername("a");
		System.out.println("Your bank account balance is $0.");
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}