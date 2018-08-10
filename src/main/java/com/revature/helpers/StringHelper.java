package com.revature.helpers;

import java.util.List;

public class StringHelper {
	public static int maxLengthString(List<String> amounts) {
		int maxLength = 0;
		for (String amount : amounts) {
			int amountLength = amount.length();
			if (amountLength > maxLength) {
				maxLength = amountLength;
			}
		}
		return maxLength;
	}
	
	public static String padRight(String oldString, int newLength) {
		return String.format("%1$-" + newLength + "s", oldString);
	}
}
