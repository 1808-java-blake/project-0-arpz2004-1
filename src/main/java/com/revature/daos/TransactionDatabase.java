package com.revature.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.BankAccount;
import com.revature.beans.BankAccount.AccountType;
import com.revature.beans.Transaction;
import com.revature.beans.Transaction.TransactionType;

public class TransactionDatabase implements TransactionDao {
	BankAccountDao bad = BankAccountDao.currentBankAccountDao;

	@Override
	public void createTransaction(Transaction t) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO bank_transactions(username, account_type, amount, "
							+ "transaction_type, transaction_time, user_transferred_to, "
							+ "account_type_transferred_to) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, t.getBankAccount().getUsername());
			ps.setString(2, t.getBankAccount().getAccountTypeString());
			ps.setBigDecimal(3, t.getAmount());
			ps.setString(4, t.getTransactionTypeString());
			ps.setTimestamp(5, Timestamp.valueOf(t.getTime()));
			ps.setString(6, t.getBankAccountTransferredTo().getUsername());
			ps.setString(7, t.getBankAccountTransferredTo().getAccountTypeString());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Transaction> findByUsernameAndAccountType(String username, AccountType accountType) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM bank_transactions WHERE username=? AND account_type=?");
			ps.setString(1, username);
			ps.setString(2, accountType.getValue());
			ResultSet rs = ps.executeQuery();
			List<Transaction> transactions = new ArrayList<>();
			while (rs.next()) {
				Transaction transaction = extractTransactionFromResultSet(rs);
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private Transaction extractTransactionFromResultSet(ResultSet rs) throws SQLException {
		BigDecimal amount = rs.getBigDecimal("amount");
		String username = rs.getString("username");
		AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
		TransactionType transactionType = TransactionType.valueOf(rs.getString("transaction_type"));
		LocalDateTime time = rs.getTimestamp("transaction_time").toLocalDateTime();
		String userTransferredTo = rs.getString("user_transferred_to");
		AccountType accountTypeTransferredTo = AccountType.valueOf(rs.getString("account_type_transferred_to"));
		BankAccount ba = bad.findByUsernameAndType(username, accountType);
		BankAccount bankAccountTransferredTo = bad.findByUsernameAndType(userTransferredTo, accountTypeTransferredTo);
		return new Transaction(amount, time, ba, transactionType, bankAccountTransferredTo);
	}

	@Override
	public void updateTransaction(Transaction t) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("UPDATE bank_transactions SET username=?, account_type=?, amount=?, "
							+ "transaction_type=?, transaction_time=?, user_transferred_to=?, account_type_transferred_to=?");
			ps.setString(1, t.getBankAccount().getUsername());
			ps.setString(2, t.getBankAccount().getAccountTypeString());
			ps.setBigDecimal(3, t.getAmount());
			ps.setString(4, t.getTransactionTypeString());
			ps.setTimestamp(5, Timestamp.valueOf(t.getTime()));
			ps.setString(6, t.getBankAccountTransferredTo().getUsername());
			ps.setString(7, t.getBankAccountTransferredTo().getAccountTypeString());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteTransaction(Transaction t) {
		// TODO Auto-generated method stub

	}

}
