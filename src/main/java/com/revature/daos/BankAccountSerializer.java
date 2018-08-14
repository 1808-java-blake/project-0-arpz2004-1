package com.revature.daos;

import java.io.File;
import com.revature.beans.BankAccount.AccountType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.revature.beans.BankAccount;

public class BankAccountSerializer implements BankAccountDao {

	@Override
	public void createBankAccount(BankAccount ba) {
		if (ba == null) {
			return;
		}
		new File("src/main/resources/bankAccounts/" + ba.getUsername()).mkdirs();
		File f = new File(
				"src/main/resources/bankAccounts/" + ba.getUsername() + "/" + ba.getAccountTypeString() + ".txt");
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
				"src/main/resources/bankAccounts/" + ba.getUsername() + "/" + ba.getAccountTypeString() + ".txt"))) {

			oos.writeObject(ba);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@Override
	public List<BankAccount> findByUsername(String username) {
		File folder = new File("src/main/resources/bankAccounts/" + username + "/");
		File[] listOfFiles = folder.listFiles();
		List<BankAccount> bankAccountList = new ArrayList<>();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(listOfFiles[i]))) {
						BankAccount ba = (BankAccount) ois.readObject();
						bankAccountList.add(ba);
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
		return bankAccountList;
	}

	@Override
	public List<BankAccount> findByUsernameAndType(Set<Entry<String, AccountType>> usernamesAndAccountTypes) {
		List<BankAccount> bankAccountList = new ArrayList<>();
		for (Entry<String, AccountType> usernameAccountType : usernamesAndAccountTypes) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/bankAccounts/"
					+ usernameAccountType.getKey() + "/" + usernameAccountType.getValue().getValue() + ".txt"))) {
				BankAccount ba = (BankAccount) ois.readObject();
				bankAccountList.add(ba);
			} catch (FileNotFoundException e) {
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return bankAccountList;
	}

	@Override
	public void updateBankAccount(BankAccount ba) {
		if (ba == null) {
			return;
		}
		File f = new File(
				"src/main/resources/bankAccounts/" + ba.getUsername() + "/" + ba.getAccountTypeString() + ".txt");
		if (!f.exists()) {
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"src/main/resources/bankAccounts/" + ba.getUsername() + "/" + ba.getAccountTypeString() + ".txt"))) {

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