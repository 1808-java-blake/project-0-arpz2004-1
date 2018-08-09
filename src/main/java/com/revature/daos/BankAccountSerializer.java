package com.revature.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.revature.beans.BankAccount;

public class BankAccountSerializer implements BankAccountDao {

	

	@Override
	public void createBankAccount(BankAccount ba) {
		if (ba == null) {
			return;
		}
		File f = new File("src/main/resources/bankAccounts/" + ba.getUsername() + ".txt");
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
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/bankAccounts/" + ba.getUsername() + ".txt"))) {

			oos.writeObject(ba);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@Override
	public BankAccount findByUsername(String username) {
		// verify that what was passed in is not null
		if (username == null) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/bankAccounts/" + username + ".txt"))) {

			BankAccount ba = (BankAccount) ois.readObject();
			return ba;

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

	@Override
	public void updateBankAccount(BankAccount ba) {
		if (ba == null) {
			return;
		}
		File f = new File("src/main/resources/bankAccounts/" + ba.getUsername() + ".txt");
		if (!f.exists()) {
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/bankAccounts/" + ba.getUsername() + ".txt"))) {

			oos.writeObject(ba);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@Override
	public void deleteBankAccount(BankAccount ba) {
		// TODO Auto-generated method stub

	}

}