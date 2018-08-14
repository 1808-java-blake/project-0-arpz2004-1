package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.User;
import com.revature.daos.UserDao;

public class ShareAccountScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;
	private BankAccount ba;

	public ShareAccountScreen() {
		super();
		AccountTypeSelectScreen accountTypeSelectScreen = new AccountTypeSelectScreen("share with another user");
		ba = accountTypeSelectScreen.getBankAccount();
	}

	@Override
	public Screen start() {
		User currentUser = User.getCurrentUser();
		User userSharedWith = null;
		String selectedUsername;
		boolean validUsername = false;
		if (ba != null) {
			do {
				System.out.println(
						"Enter username of user to share account with or press enter to return to home screen.");
				selectedUsername = scan.nextLine();
				if (selectedUsername.isEmpty()) {
					validUsername = true;
				} else {
					userSharedWith = ud.findByUsername(selectedUsername);
					if (userSharedWith == null) {
						System.out.println("ERROR: User with that username does not exist.");
					} else {
						if (selectedUsername.equals(currentUser.getUsername())) {
							System.out.println("ERROR: You can't share an account with yourself.");
						} else {
							validUsername = true;
							boolean success = userSharedWith.addSharedAccount(currentUser.getUsername(),
									ba.getAccountType()) && !userSharedWith.getUsername().equals(ba.getUsername());
							if (success) {
								ud.updateUser(userSharedWith);
								System.out.println("Account successfully shared with " + userSharedWith.getUsername()
										+ ". Press enter to return to home screen.");
							} else {
								System.out.println(
										"ERROR: This account was already shared with that user. Press enter to return to the home screen.");
							}
							scan.nextLine();
						}
					}
				}
			} while (!validUsername);
		}
		return new HomeScreen();
	}

}