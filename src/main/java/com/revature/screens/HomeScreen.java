package com.revature.screens;

import java.util.Scanner;

public class HomeScreen implements Screen {

	private Scanner scan = new Scanner(System.in);

	public Screen start() {
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Please choose from following options:");
			System.out.println("Enter 1 to deposit money.");
			System.out.println("Enter 2 withdraw money.");
			System.out.println("Enter 3 to view account balance.");
			System.out.println("Enter 4 to view transaction history.");
			System.out.println("Enter 5 to logout.");
			selection = scan.nextLine();
			if (selection.length() == 1) {
				char c = selection.charAt(0);
				if (Character.isDigit(c)) {
					int valueOfCharacter = Character.getNumericValue(c);
					validSelection = valueOfCharacter >= 1 && valueOfCharacter <= 5;
				}
			}
			if(!validSelection) {
				System.out.println("Invalid option selected.");
			}
		} while (!validSelection);
		switch (selection) {
		case "1":
			System.out.println("selected 1 not yet implemented that screen");
			break;
		case "2":
			System.out.println("selected 2 not yet implemented that screen");
			break;
		case "3":
			System.out.println("selected 3 not yet implemented that screen");
			break;
		case "4":
			System.out.println("selected 4 not yet implemented that screen");
			break;
		case "5":
			return new LoginScreen();
		}
		return this;
	}
}