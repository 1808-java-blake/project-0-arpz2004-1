package com.revature.screens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.beans.User;
import com.revature.daos.UserDao;
import com.revature.helpers.StringHelper;

public class AllUsersScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public Screen start() {
		Map<Integer, User> findUser = new HashMap<>();
		List<User> userList = ud.getAllUsers();
		int userCount = userList.size();
		int maxCountLength = String.valueOf(userCount).length() + 3;
		System.out.println(StringHelper.padRight("#", maxCountLength) + "Username");
		int userNumber = 0;
		for (User user : userList) {
			userNumber++;
			findUser.put(userNumber, user);
			System.out.println(StringHelper.padRight(String.valueOf(userNumber), maxCountLength) + user.getUsername());
		}
		String selection;
		boolean validSelection = false;
		do {
			System.out.println("Enter user's # to view user or press enter to return to admin screen.");
			selection = scan.nextLine();
			if (selection.matches("^\\d+$")) {
				int userNum = Integer.parseInt(selection);
				if(userNum >= 1 && userNum <= userCount) {
					return new ShowUserScreen(findUser.get(userNum));
				} else {
					System.out.println("Invalid number entered. Value must be between 1 and " + userCount);
				}
			} else if(selection.isEmpty()) {
				validSelection = true;
			} else {
				System.out.println("Invalid value entered.");
			}
		} while (!validSelection);
		return new AdminScreen();
	}
}