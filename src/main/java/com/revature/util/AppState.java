package com.revature.util;

import com.revature.beans.User;

public class AppState {
	public static final AppState state = new AppState();
	private User currentUser;

	private AppState() {

	}
	
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
