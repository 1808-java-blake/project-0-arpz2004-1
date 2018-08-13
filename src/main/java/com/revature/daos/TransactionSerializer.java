package com.revature.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.revature.beans.Transaction;
import com.revature.beans.User;

public class TransactionSerializer implements TransactionDao {
	private UserDao ud = UserDao.currentUserDao;

	@Override
	public void createTransaction(Transaction t) {
		if (t == null) {
			return;
		}
		User user = t.getUser();
		String username = user.getUsername();
		new File("src/main/resources/transactions/" + username).mkdirs();
		File f = new File("src/main/resources/transactions/" + username + "/" + t.getTransactionID() + ".txt");
		if (f.exists()) {
			return;
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"src/main/resources/transactions/" + username + "/" + t.getTransactionID() + ".txt"))) {

			oos.writeObject(t);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ud.updateUser(user);

	}

	@Override
	public Transaction findByUserAndID(User u, int transactionID) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				"src/main/resources/transactions/" + u.getUsername() + "/" + transactionID + ".txt"))) {
			Transaction t = (Transaction) ois.readObject();
			return t;
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Transaction file not found.");
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

	@Override
	public void updateTransaction(Transaction t) {
		if (t == null) {
			return;
		}
		User user = t.getUser();
		String username = user.getUsername();
		File f = new File("src/main/resources/transactions/" + username + "/" + t.getTransactionID() + ".txt");
		if (!f.exists()) {
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"src/main/resources/transactions/" + username + "/" + t.getTransactionID() + ".txt"))) {

			oos.writeObject(t);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		ud.updateUser(user);

	}

	@Override
	public void deleteTransaction(Transaction t) {
	}

}