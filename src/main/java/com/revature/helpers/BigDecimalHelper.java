package com.revature.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalHelper {
	public static String getMoneyString(BigDecimal bd) {
		String amountString = "$";
		BigDecimal amount = bd;
		if (amount.signum() == -1) {
			amountString = "-" + amountString;
			amount = amount.multiply(new BigDecimal(-1));
		}
		return amountString + amount.setScale(2, RoundingMode.HALF_UP);
	}
}
