package com.revature.screens;

import java.util.List;
import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.helpers.StringHelper;

public class AllUsersScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		List<User> userList = ud.getAllUsers();
		int userCount = userList.size();
		int maxCountLength = String.valueOf(userCount).length() + 3;
		System.out.println(StringHelper.padRight("#", maxCountLength) + "Username");
		int userNumber = 0;
		for (User user : userList) {
			userNumber++;
			System.out.println(StringHelper.padRight(String.valueOf(userNumber), maxCountLength) + user.getUsername());
		}
		System.out.println("Press enter to return to admin screen.");
		scan.nextLine();
		return new AdminScreen();
	}
}