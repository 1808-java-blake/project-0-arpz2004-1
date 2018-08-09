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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println("#    Time                  Amount");
		for (Integer transactionID : transactionHistory) {
			Transaction transaction = td.findByUserAndID(u, transactionID);
			String transactionTime = transaction.getTime().format(formatter);
			System.out.println(
					transaction.getTransactionID() + "    " + transactionTime + "   " + transaction.getAmount());
		}
		System.out.println("Press enter to return to home screen.");
		scan.nextLine();
		return new HomeScreen();
	}
}