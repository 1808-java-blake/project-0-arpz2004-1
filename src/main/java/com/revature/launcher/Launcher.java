package com.revature.launcher;

import com.revature.screens.LoginScreen;
import com.revature.screens.Screen;

public class Launcher {
	public static void main(String[] args) {
		Screen s = new LoginScreen();
		while(true) {
			s = s.start();
		}
	}
}
