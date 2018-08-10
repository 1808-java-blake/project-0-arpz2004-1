package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.BankAccount;
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
			System.out.print("Enter new username: ");
			username = scan.nextLine();
			if (username.isEmpty()) {
				System.out.println("Username must be at least one character long.");
			} else {
				if(ud.findByUsername(username) == null) {
					u.setUsername(username);
				}else {
					username = "";
					System.out.println("Username already exists. Please choose a different one.");
				}
			}
		}
		if(username.equals("admin")) {
			u.setAdminLevel(1);
		}
		String password = "";
		while (password.length() < 6) {
			System.out.print("Enter password: ");
			password = scan.nextLine();
			if (password.length() < 6) {
				System.out.println("Password must be at least six characters long.");
			} else {
				System.out.print("Confirm password: ");
				String confirmPassword = scan.nextLine();
				if (confirmPassword.equals(password)) {
					u.setPassword(password);
				} else {
					password = "";
					System.out.println("Passwords do not match. Please try again.");
				}
			}
		}
		String firstName = "";
		while (firstName.isEmpty()) {
			System.out.print("Enter first name: ");
			firstName = scan.nextLine();
			if (firstName.isEmpty()) {
				System.out.println("First name must be at least one character long.");
			} else {
				u.setFirstName(firstName);
			}
		}
		String lastName = "";
		while (lastName.isEmpty()) {
			System.out.print("Enter last name: ");
			lastName = scan.nextLine();
			if (lastName.isEmpty()) {
				System.out.println("Last name must be at least one character long.");
			} else {
				u.setLastName(lastName);
			}
		}
		String age = "";
		while (age.isEmpty()) {
			System.out.println("Enter age: ");
			age = scan.nextLine();
			if (age.matches("\\d+") && Integer.parseInt(age) >= 0 && Integer.parseInt(age) <= 150) {
				u.setAge(Integer.parseInt(age));
			} else {
				age = "";
				System.out.println("Invalid age. You must enter a number between 0 and 150.");
			}
		}
		ud.createUser(u);
		BankAccount ba = new BankAccount();
		ba.setUsername(username);
		ba.setBalance(0);
		bad.createBankAccount(ba);

		return new LoginScreen();
	}

}