package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;

public class BankAccountDatabase implements BankAccountDao {
	@Override
	public void createBankAccount(BankAccount ba) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO bank_account(username, account_type, balance) VALUES (?, ?::account_type, ?)");
			ps.setString(1, ba.getUsername());
			ps.setString(2, ba.getAccountType().toString());
			ps.setBigDecimal(3, ba.getBalance());
			if(ps.executeUpdate() != 1) {
				System.out.println("Error creating bank account.");
			}
			ps.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<BankAccount> findByUsername(String username) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM bank_account WHERE username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			List<BankAccount> bankAccounts = new ArrayList<>();
			while (rs.next()) {
				BankAccount bankAccount = extractBankAccountFromResultSet(rs);
				bankAccounts.add(bankAccount);
			}
			rs.close();
			ps.close();
			connection.close();
			return bankAccounts;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BankAccount> findByUsernameAndType(Set<Entry<String, AccountType>> usernamesAndAccountTypes) {
		Connection connection = ConnectionFactory.getConnection();
		List<BankAccount> bankAccounts = new ArrayList<>();
		for (Entry<String, AccountType> usernameAccountType : usernamesAndAccountTypes) {
			try {
				PreparedStatement ps = connection
						.prepareStatement("SELECT * FROM bank_account WHERE username=? AND account_type=?::account_type");
				ps.setString(1, usernameAccountType.getKey());
				ps.setString(2, usernameAccountType.getValue().toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					BankAccount bankAccount = extractBankAccountFromResultSet(rs);
					bankAccounts.add(bankAccount);
				}
				rs.close();
				ps.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return bankAccounts;
	}

	@Override
	public BankAccount findByUsernameAndType(String username, AccountType accountType) {
		Connection connection = ConnectionFactory.getConnection();
		BankAccount bankAccount = null;
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM bank_account WHERE username=? AND account_type=?::account_type");
			ps.setString(1, username);
			ps.setString(2, accountType.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bankAccount = extractBankAccountFromResultSet(rs);
			}
			rs.close();
			ps.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bankAccount;
	}

	private BankAccount extractBankAccountFromResultSet(ResultSet rs) throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		BankAccount bankAccount = new BankAccount();
		String username = rs.getString("username");
		bankAccount.setUsername(username);
		AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
		bankAccount.setAccountType(accountType);
		bankAccount.setBalance(rs.getBigDecimal("balance"));
		TreeSet<Integer> transactionHistory = new TreeSet<>();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT transaction_id FROM bank_account NATURAL JOIN bank_transaction"
							+ " WHERE username=? AND account_type=?::account_type");
			ps.setString(1, username);
			ps.setString(2, accountType.toString());
			ResultSet rsBA = ps.executeQuery();
			while (rsBA.next()) {
				transactionHistory.add(rsBA.getInt("transaction_id"));
			}
			bankAccount.setTransactionHistory(transactionHistory);
			rsBA.close();
			ps.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bankAccount;
	}

	@Override
	public void updateBankAccount(BankAccount ba) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("UPDATE bank_account SET balance=? WHERE username=? AND account_type=?::account_type");
			ps.setBigDecimal(1, ba.getBalance());
			ps.setString(2, ba.getUsername());
			ps.setString(3, ba.getAccountType().toString());
			if(ps.executeUpdate() != 1) {
				System.out.println("Error updating bank account.");
			}
			ps.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteBankAccount(BankAccount ba) {
		// TODO Auto-generated method stub

	}

}
