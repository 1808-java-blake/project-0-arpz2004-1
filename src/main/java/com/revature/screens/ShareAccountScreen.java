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
		boolean sharableAccount = false;
		while (!sharableAccount) {
			AccountTypeSelectScreen accountTypeSelectScreen = new AccountTypeSelectScreen("share with another user");
			ba = accountTypeSelectScreen.getBankAccount();
			sharableAccount = ba.getUsername().equals(User.getCurrentUser().getUsername());
			if (!sharableAccount) {
				ba = null;
				boolean validSelection = false;
				String selection;
				System.out.println("You can't share an account that was shared with you. You can only share accounts you created.");
				do {
					System.out.println("Type 1 to try again or type 2 to return to home screen.");
					selection = scan.nextLine();
					if (selection.length() == 1) {
						char c = selection.charAt(0);
						if (Character.isDigit(c)) {
							int valueOfCharacter = Character.getNumericValue(c);
							validSelection = valueOfCharacter >= 1 && valueOfCharacter <= 2;
						}
					}
					if (!validSelection) {
						System.out.println("Invalid option selected.");
					}
				} while (!validSelection);
				if(selection.equals("2")) {
					sharableAccount = true;
				}
			}
		}
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