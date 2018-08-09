package com.revature.screens;

import java.math.BigDecimal;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;

public class WithdrawMoneyScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public Screen start() {
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Enter the amount you want to withdraw:");
			selection = scan.nextLine();
			validSelection = true;
			if(!validSelection) {
				System.out.println("Invalid option selected.");
			}
		} while (!validSelection);
		BankAccount ba = bad.findByUsername(User.getCurrentUser().getUsername());
		ba.setBalance(ba.getBalance().subtract(new BigDecimal(Integer.parseInt(selection))));
		bad.updateBankAccount(ba);
		System.out.println("Your new bank account balance is $" + ba.getBalance() + ".");
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}