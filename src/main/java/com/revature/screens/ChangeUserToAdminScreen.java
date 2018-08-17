package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;

public class ChangeUserToAdminScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		String username;
		boolean validUsername = false;
		do {
			System.out.println("Enter username of user to change to admin or press enter to return to admin screen.");
			username = scan.nextLine();
			if (username.isEmpty()) {
				validUsername = true;
			} else {
				User u = ud.findByUsername(username);
				if (u == null) {
					System.out.println("ERROR: User with that username does not exist.");
				} else if (u.isAdmin()) {
					System.out.println("ERROR: User with that username is already an admin.");
				} else {
					System.out.println(
							"Enter username again to confirm you want to make this user an admin or press enter to return to admin screen.");
					String confirmUsername = scan.nextLine();
					if (confirmUsername.isEmpty()) {
						validUsername = true;
					} else if (confirmUsername.equals(username)) {
						u.setAdminLevel(1);
						ud.updateUser(u);
						validUsername = true;
						System.out.println(u.getUsername()
								+ " has successfully been made an admin. Press enter to return to admin screen.");
						scan.nextLine();
					} else {
						validUsername = false;
						System.out.println("Usernames do not match. Please try again.");
					}
				}
			}
		} while (!validUsername);
		return new AdminScreen();
	}

}
