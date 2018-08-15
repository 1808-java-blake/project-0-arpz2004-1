package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;

public class BankAccountDatabase implements BankAccountDao {

	@Override
	public void createBankAccount(BankAccount ba) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO bank_accounts(username, account_type, balance) VALUES (?, ?, ?)");
			ps.setString(1, ba.getUsername());
			ps.setString(2, ba.getAccountTypeString());
			ps.setBigDecimal(3, ba.getBalance());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<BankAccount> findByUsername(String username) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM bank_accounts WHERE username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			List<BankAccount> bankAccounts = new ArrayList<>();
			while (rs.next()) {
				BankAccount bankAccount = extractBankAccountFromResultSet(rs);
				bankAccounts.add(bankAccount);
			}
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
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM bank_accounts WHERE username=? AND account_type=?");
				ps.setString(1, usernameAccountType.getKey());
				ps.setString(2, usernameAccountType.getValue().getValue());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					BankAccount bankAccount = extractBankAccountFromResultSet(rs);
					bankAccounts.add(bankAccount);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return bankAccounts;
	}
	
	private BankAccount extractBankAccountFromResultSet(ResultSet rs) throws SQLException {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setUsername(rs.getString("username"));
		bankAccount.setAccountType(AccountType.valueOf(rs.getString("account_type")));
		bankAccount.setBalance(rs.getBigDecimal("balance"));
		return bankAccount;
	}

	@Override
	public void updateBankAccount(BankAccount ba) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE bank_accounts SET username=?, account_type=?, balance=?");
			ps.setString(1, ba.getUsername());
			ps.setString(2, ba.getAccountTypeString());
			ps.setBigDecimal(3, ba.getBalance());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteBankAccount(BankAccount ba) {
		// TODO Auto-generated method stub
		
	}

}
