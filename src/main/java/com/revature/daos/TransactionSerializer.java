package com.revature.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.BankAccount.AccountType;
import com.revature.beans.Transaction;

public class TransactionSerializer implements TransactionDao {
	@Override
	public void createTransaction(Transaction t) {
		if (t == null) {
			return;
		}
		String username = t.getBankAccount().getUsername();
		String accountType = t.getBankAccount().getAccountTypeString();
		new File("src/main/resources/transactions/" + username + "/" + accountType).mkdirs();
		File f = new File("src/main/resources/transactions/" + username + "/" + accountType + "/" + t.getTransactionID()
				+ ".txt");
		if (f.exists()) {
			return;
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/transactions/"
				+ username + "/" + accountType + "/" + t.getTransactionID() + ".txt"))) {

			oos.writeObject(t);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Transaction> findByUsernameAndAccountType(String username, AccountType accountType) {
		File folder = new File("src/main/resources/transactions/" + username + "/" + accountType + "/");
		File[] listOfFiles = folder.listFiles();
		List<Transaction> transactionList = new ArrayList<>();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(listOfFiles[i]))) {
						Transaction t = (Transaction) ois.readObject();
						transactionList.add(t);
					} catch (FileNotFoundException e) {
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return transactionList;
	}

	@Override
	public void updateTransaction(Transaction t) {
		if (t == null) {
			return;
		}
		String username = t.getBankAccount().getUsername();
		String accountType = t.getBankAccount().getAccountTypeString();
		File f = new File("src/main/resources/transactions/" + username + "/" + accountType + "/" + t.getTransactionID()
				+ ".txt");
		if (!f.exists()) {
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/transactions/"
				+ username + "/" + accountType + "/" + t.getTransactionID() + ".txt"))) {

			oos.writeObject(t);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	@Override
	public void deleteTransaction(Transaction t) {
	}

}