package com.revature.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.revature.beans.Transaction;

public class TransactionSerializer implements TransactionDao {

	@Override
	public void createTransaction(Transaction t) {
		if (t == null) {
			return;
		}
		File f = new File("src/main/resources/transactions/" + t.getTime() + "_" + t.getAmount() + ".txt");
		if (f.exists()) {
			return;
		}
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"src/main/resources/transactions/" + t.getTime() + "_" + t.getAmount() + ".txt"))) {

			oos.writeObject(t);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@Override
	public Transaction findByDateAndAmount(LocalDateTime date, BigDecimal amount) {
		// verify that what was passed in is not null
		if (date == null || amount == null) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/transactions/" + date + "_" + amount + ".txt"))) {

			Transaction t = (Transaction) ois.readObject();
			return t;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateTransaction(Transaction t) {
		if (t == null) {
			return;
		}
		File f = new File("src/main/resources/transactions/" + t.getTime() + "_" + t.getAmount() + ".txt");
		if (!f.exists()) {
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"src/main/resources/transactions/" + t.getTime() + "_" + t.getAmount() + ".txt"))) {

			oos.writeObject(t);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@Override
	public void deleteTransaction(Transaction t) {
		// TODO Auto-generated method stub

	}

}