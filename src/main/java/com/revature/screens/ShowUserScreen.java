package com.revature.screens;

import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.helpers.StringHelper;

public class ShowUserScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;
	private User user;

	public ShowUserScreen(User user) {
		this.user = user;
	}

	@Override
	public Screen start() {
		User u = ud.findByUsername(user.getUsername());
		String username = u.getUsername();
		String firstName = u.getFirstName();
		String lastName = u.getLastName();
		int age = u.getAge();
		int adminLevel = u.getAdminLevel();
		int usernameLength = Math.max(7, username.length()) + 3;
		int firstNameLength = Math.max(10, firstName.length()) + 3;
		int lastNameLength = Math.max(9, firstName.length()) + 3;
		int ageLength = Math.max(3, String.valueOf(age).length()) + 3;
		System.out.println(
				StringHelper.padRight("Username", usernameLength) + StringHelper.padRight("First Name", firstNameLength)
						+ StringHelper.padRight("Last Name", lastNameLength) + StringHelper.padRight("Age", ageLength)
						+ "Admin");
		System.out.println(StringHelper.padRight(username, usernameLength)
				+ StringHelper.padRight(firstName, firstNameLength) + StringHelper.padRight(lastName, lastNameLength)
				+ StringHelper.padRight(String.valueOf(age), ageLength) + (adminLevel == 1 ? "Yes" : "No"));
		System.out.println("Enter 1 to view user's transaction history or anything else to return to admin screen.");
		String selection = scan.nextLine();
		if("1".equals(selection)) {
			return new TransactionHistoryScreen(user, new AdminScreen());
		}
		return new AdminScreen();
	}

}