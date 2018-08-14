package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;

public class HomeScreen implements Screen {

	private Scanner scan = new Scanner(System.in);

	public Screen start() {
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Please choose from following options:");
			boolean admin = User.getCurrentUser() != null && User.getCurrentUser().isAdmin();
			if(admin) {
				System.out.println("Enter 0 to view admin screen.");
			}
			System.out.println("Enter 1 to deposit money.");
			System.out.println("Enter 2 to withdraw money.");
			System.out.println("Enter 3 to view account balance.");
			System.out.println("Enter 4 to view transaction history.");
			System.out.println("Enter 5 to start a wire transfer.");
			System.out.println("Enter 6 to create a new type of account.");
			System.out.println("Enter 7 to share an account with another user.");
			System.out.println("Enter 8 to logout.");
			selection = scan.nextLine();
			if (selection.length() == 1) {
				char c = selection.charAt(0);
				if (Character.isDigit(c)) {
					int valueOfCharacter = Character.getNumericValue(c);
					validSelection = valueOfCharacter >= 1 && valueOfCharacter <= 8;
					if(admin) {
						validSelection |= valueOfCharacter == 0;
					}
				}
			}
			if(!validSelection) {
				System.out.println("Invalid option selected.");
			}
		} while (!validSelection);
		switch (selection) {
		case "0":
			return new AdminScreen();
		case "1":
			return new DepositMoneyScreen();
		case "2":
			return new WithdrawMoneyScreen();
		case "3":
			return new AccountBalanceScreen();
		case "4":
			return new TransactionHistoryScreen();
		case "5":
			return new WireTransferScreen();
		case "6":
			return new NewAccountTypeScreen();
		case "7":
			return new ShareAccountScreen();
		case "8":
			User.setCurrentUser(null);
			return new LoginScreen();
		}
		return this;
	}
}
