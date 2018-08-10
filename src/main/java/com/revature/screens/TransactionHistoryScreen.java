package com.revature.screens;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.daos.TransactionDao;

public class TransactionHistoryScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private TransactionDao td = TransactionDao.currentTransactionDao;

	@Override
	public Screen start() {
		User u = User.getCurrentUser();
		List<Integer> transactionHistory = u.getTransactionHistory();
		int lastTransactionID = transactionHistory.get(transactionHistory.size() - 1);
		int numberOfDigits = String.valueOf(lastTransactionID).length();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(padRight("#", numberOfDigits + 3) + padRight("Time", 22) + "Amount");
		for (Integer transactionID : transactionHistory) {
			Transaction transaction = td.findByUserAndID(u, transactionID);
			String transactionTime = transaction.getTime().format(formatter);
			System.out.println(padRight(String.valueOf(transaction.getTransactionID()), numberOfDigits + 3)
					+ padRight(transactionTime, 22) + transaction.getAmountString());
		}
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

	private String padRight(String oldString, int newLength) {
		return String.format("%1$-" + newLength + "s", oldString);
	}
}