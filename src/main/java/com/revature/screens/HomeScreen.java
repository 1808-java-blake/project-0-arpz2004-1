package com.revature.screens;

import java.util.Scanner;

public class HomeScreen implements Screen {
	
	private Scanner scan = new Scanner(System.in);

	public Screen start() {
		System.out.println("Please choose from following options:");
		System.out.println("Enter 1 to build a weapon");
		System.out.println("Enter 2 to view available weapons");
		System.out.println("Enter 3 to view my weapons");
		System.out.println("Enter 4 to sell a weapon");
		String selection = scan.nextLine();
		switch(selection) {
		case "1":
			System.out.println("selected 1 not yet implemented that screen");
			break;
		case "2":
			System.out.println("selected 1 not yet implemented that screen");
			break;
		case "3":
			System.out.println("selected 1 not yet implemented that screen");
			break;
		case "4":
			System.out.println("selected 1 not yet implemented that screen");
			break;
		}
		return this;
	}
}
