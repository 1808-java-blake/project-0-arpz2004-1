package com.revature.screens;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.Transaction;
import com.revature.beans.User;

public class TransactionHistoryScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	@Override
	public Screen start() {
		User u = User.getCurrentUser();
		List<Transaction> transactionHistory = u.getTransactionHistory();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for(Transaction transaction : transactionHistory) {
	        String transactionTime = transaction.getTime().format(formatter);
			System.out.println(transactionTime + "   " + transaction.getAmount());
		}
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}

}