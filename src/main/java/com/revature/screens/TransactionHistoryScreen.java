package com.revature.screens;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;
import com.revature.beans.Transaction;
import com.revature.beans.User;
import com.revature.daos.TransactionDao;
import com.revature.helpers.BigDecimalHelper;
import com.revature.helpers.StringHelper;

public class TransactionHistoryScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private TransactionDao td = TransactionDao.currentTransactionDao;
	private Screen previousScreen;
	private BankAccount ba;

	public TransactionHistoryScreen() {
		previousScreen = new HomeScreen();
		AccountTypeSelectScreen accountTypeSelectScreen = new AccountTypeSelectScreen("deposit money to");
		ba = accountTypeSelectScreen.getBankAccount();
	}

	public TransactionHistoryScreen(User user, Screen screen) {
		previousScreen = screen;
		AccountTypeSelectScreen accountTypeSelectScreen = new AccountTypeSelectScreen(user, "deposit money to");
		ba = accountTypeSelectScreen.getBankAccount();
	}

	@Override
	public Screen start() {
		if (ba != null) {
			List<Transaction> transactionHistory = td.findByUsernameAndAccountType(ba.getUsername(),
					ba.getAccountType());
			List<String> transactionAmounts = new ArrayList<>();
			List<String> transferredToUsers = new ArrayList<>();
			int maxTransactionID = 0;
			for (Transaction transaction : transactionHistory) {
				transactionAmounts.add(BigDecimalHelper.getMoneyString(transaction.getAmount()));
				String bankAccountTransferredTo = transaction.getUsernameAndAccountTypeTransferredTo();
				if (bankAccountTransferredTo != null) {
					transferredToUsers.add(bankAccountTransferredTo);
				}
				int transactionID = transaction.getTransactionID();
				if (transactionID > maxTransactionID) {
					maxTransactionID = transactionID;
				}
			}
			int maxIDLength = String.valueOf(maxTransactionID).length() + 3;
			int maxAmountLength = StringHelper.maxLengthString(transactionAmounts);
			int dateLength = 7;
			if (transactionHistory.size() > 0) {
				dateLength = 22;
			}
			int amountLength = Math.max(9, maxAmountLength + 3);
			int accountTypeLength = 15;
			int transactionTypeLength = 19;
			int maxTransferToLength = StringHelper.maxLengthString(transferredToUsers);
			int transferToLength = Math.max(22, maxTransferToLength + 3);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			System.out.println(
					StringHelper.padRight("#", maxIDLength) + StringHelper.padRight("Account Type", accountTypeLength)
							+ StringHelper.padRight("Transaction Type", transactionTypeLength)
							+ StringHelper.padRight("Transferred To/From", transferToLength)
							+ StringHelper.padRight("Time", dateLength) + StringHelper.padRight("Amount", amountLength)
							+ "Balance");
			Map<AccountType, BigDecimal> accountTypeBalance = new HashMap<>();
			for (Transaction transaction : transactionHistory) {
				AccountType accountType = transaction.getBankAccount().getAccountType();
				BigDecimal newAccountTypeBalance = transaction.getAmount()
						.add(accountTypeBalance.getOrDefault(accountType, new BigDecimal(0)));
				accountTypeBalance.put(accountType, newAccountTypeBalance);
				String transactionTime = transaction.getTime().format(formatter);
				String bankAccountTransferredTo = transaction.getUsernameAndAccountTypeTransferredTo();
				if (bankAccountTransferredTo == null) {
					bankAccountTransferredTo = "";
				}
				System.out.println(StringHelper.padRight(String.valueOf(transaction.getTransactionID()), maxIDLength)
						+ StringHelper.padRight(transaction.getBankAccount().getAccountTypeString(), accountTypeLength)
						+ StringHelper.padRight(transaction.getTransactionTypeString(), transactionTypeLength)
						+ StringHelper.padRight(bankAccountTransferredTo, transferToLength)
						+ StringHelper.padRight(transactionTime, dateLength)
						+ StringHelper.padRight(transaction.getAmountString(), amountLength)
						+ BigDecimalHelper.getMoneyString(newAccountTypeBalance));
			}
			if (previousScreen instanceof ShowUserScreen) {
				System.out.println("Press enter to return to show user screen.");
			} else {
				System.out.println("Press enter to return to home screen.");
			}
			scan.nextLine();
		}
		return previousScreen;
	}
}