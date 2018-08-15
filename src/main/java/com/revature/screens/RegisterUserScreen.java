package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;
import com.revature.beans.User;
import com.revature.daos.BankAccountDao;
import com.revature.daos.UserDao;

public class RegisterUserScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;
	private BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public Screen start() {
		User u = new User();
		String username = "";
		while (username.isEmpty()) {
			System.out.println("Enter new username or type quit to cancel registration and return to login screen: ");
			username = scan.nextLine();
			if(username.equals("quit")) {
				return new LoginScreen();
			}
			if (username.isEmpty()) {
				System.out.println("Username must be at least one character long.");
			} else {
				if (ud.findByUsername(username) == null) {
					u.setUsername(username);
				} else {
					username = "";
					System.out.println("Username already exists. Please choose a different one.");
				}
			}
		}
		if (username.equals("admin")) {
			u.setAdminLevel(1);
		}
		String password = "";
		boolean validPassword = false;
		while (!validPassword) {
			System.out.println("Enter password or type quit to return to login screen: ");
			password = scan.nextLine();
			if(password.equals("quit")) {
				return new LoginScreen();
			}
			validPassword = (!username.equals("admin") && password.length() >= 6)
					|| (username.equals("admin") && password.equals("admin"));
			if (validPassword) {
				System.out.println("Confirm password or type quit to cancel registration and return to login screen: ");
				String confirmPassword = scan.nextLine();
				if(confirmPassword.equals("quit")) {
					return new LoginScreen();
				}
				if (confirmPassword.equals(password)) {
					u.setPassword(password);
				} else {
					validPassword = false;
					System.out.println("Passwords do not match. Please try again.");
				}
			} else {
				if (username.equals("admin")) {
					System.out.println("Invalid admin password. Press enter to return to login screen.");
					scan.nextLine();
					return new LoginScreen();
				} else {
					System.out.println("Password must be at least six characters long.");
				}
			}
		}
		String firstName = "";
		while (firstName.isEmpty()) {
			System.out.println("Enter first name or type quit to cancel registration and return to login screen: ");
			firstName = scan.nextLine();
			if(firstName.equals("quit")) {
				return new LoginScreen();
			}
			if (firstName.isEmpty()) {
				System.out.println("First name must be at least one character long.");
			} else {
				u.setFirstName(firstName);
			}
		}
		String lastName = "";
		while (lastName.isEmpty()) {
			System.out.println("Enter last name or type quit to cancel registration and return to login screen: ");
			lastName = scan.nextLine();
			if(lastName.equals("quit")) {
				return new LoginScreen();
			}
			if (lastName.isEmpty()) {
				System.out.println("Last name must be at least one character long.");
			} else {
				u.setLastName(lastName);
			}
		}
		String age = "";
		while (age.isEmpty()) {
			System.out.println("Enter age or type quit to cancel registration and return to login screen: ");
			age = scan.nextLine();
			if(age.equals("quit")) {
				return new LoginScreen();
			}
			if (age.matches("\\d+") && Integer.parseInt(age) >= 0 && Integer.parseInt(age) <= 150) {
				u.setAge(Integer.parseInt(age));
			} else {
				age = "";
				System.out.println("Invalid age. You must enter a number between 0 and 150.");
			}
		}
		BankAccount ba = null;
		String accountType = "";
		while (accountType.isEmpty()) {
			boolean validSelection = false;
			do {
				System.out.println("Please choose the type of account to create:");
				System.out.println("Enter 1 to create a savings account.");
				System.out.println("Enter 2 to create a checking account.");
				System.out.println("Enter 3 to cancel registration and return to login screen.");
				accountType = scan.nextLine();
				if (accountType.length() == 1) {
					char c = accountType.charAt(0);
					if (Character.isDigit(c)) {
						int valueOfCharacter = Character.getNumericValue(c);
						validSelection = valueOfCharacter >= 1 && valueOfCharacter <= 3;
					}
				}
				if (!validSelection) {
					accountType = "";
					System.out.println("Invalid option selected.");
				}
			} while (!validSelection);
			switch (accountType) {
			case "1":
				ba = new BankAccount(username, AccountType.SAVINGS);
				break;
			case "2":
				ba = new BankAccount(username, AccountType.CHECKING);
				break;
			case "3":
				return new LoginScreen();
			}
		}
		ud.createUser(u);
		bad.createBankAccount(ba);
		System.out.println("Registration finished. Press enter to return to login screen.");
		scan.nextLine();
		return new LoginScreen();
	}

}