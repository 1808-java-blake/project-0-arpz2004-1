package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;

public class SearchForUserScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Enter username of user to view or press enter to return to admin screen.");
			selection = scan.nextLine();
			if (selection.isEmpty()) {
				validSelection = true;
			} else {
				User u = ud.findByUsername(selection);
				if (u == null) {
					System.out.println("User with that username does not exist.");
				} else {
					return new ShowUserScreen(u);
				}
			}
		} while (!validSelection);
		return new AdminScreen();
	}

}