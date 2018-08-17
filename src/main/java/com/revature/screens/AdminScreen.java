package com.revature.screens;

import java.util.Scanner;

public class AdminScreen implements Screen {

	private Scanner scan = new Scanner(System.in);

	public Screen start() {
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Please choose from following options:");
			System.out.println("Enter 1 to view all users.");
			System.out.println("Enter 2 to search for a user by username.");
			System.out.println("Enter 3 to change a user to an admin.");
			System.out.println("Enter 4 to return to home screen.");
			selection = scan.nextLine();
			if (selection.length() == 1) {
				char c = selection.charAt(0);
				if (Character.isDigit(c)) {
					int valueOfCharacter = Character.getNumericValue(c);
					validSelection = valueOfCharacter >= 1 && valueOfCharacter <= 4;
				}
			}
			if (!validSelection) {
				System.out.println("Invalid option selected.");
			}
		} while (!validSelection);
		switch (selection) {
		case "1":
			return new AllUsersScreen();
		case "2":
			return new SearchForUserScreen();
		case "3":
			return new ChangeUserToAdminScreen();
		case "4":
			return new HomeScreen();
		}
		return this;
	}
}
