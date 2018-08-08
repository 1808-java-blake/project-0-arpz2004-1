package com.revature.screens;

import java.math.BigDecimal;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;

public class DepositMoneyScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public Screen start() {
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Enter the amount you want to deposit:");
			selection = scan.nextLine();
			validSelection = true;
			if(!validSelection) {
				System.out.println("Invalid option selected.");
			}
		} while (!validSelection);
		BankAccount ba = bad.findByUsername(User.getCurrentUser().getUsername());
		ba.setBalance(ba.getBalance().add(new BigDecimal(Integer.parseInt(selection))));
		System.out.println("Your new bank account balance is $" + ba.getBalance() + ".");
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}