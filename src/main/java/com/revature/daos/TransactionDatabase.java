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
					.prepareStatement("INSERT INTO bank_transaction(transaction_id, username, account_type, amount, "
							+ "transaction_type, transaction_time, user_transferred_to, account_type_transferred_to) "
							+ "VALUES (?, ?, ?::account_type, ?, ?::transaction_type, ?, ?, ?::account_type)");
			ps.setInt(1, t.getTransactionID());
			ps.setString(2, t.getBankAccount().getUsername());
			ps.setString(3, t.getBankAccount().getAccountType().toString());
			ps.setBigDecimal(4, t.getAmount());
			ps.setString(5, t.getTransactionType().toString());
			ps.setTimestamp(6, Timestamp.valueOf(t.getTime()));
			BankAccount baTransfer = t.getBankAccountTransferredTo();
			String userTransferredTo = null;
			String accountTypeTransferredTo = null;
			if (baTransfer != null) {
				userTransferredTo = baTransfer.getUsername();
				accountTypeTransferredTo = baTransfer.getAccountType().toString();
			}
			ps.setString(7, userTransferredTo);
			ps.setString(8, accountTypeTransferredTo);
			if (ps.executeUpdate() != 1) {
				System.out.println("Error creating transaction.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Transaction> findByUsernameAndAccountType(String username, AccountType accountType) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"SELECT * FROM bank_transaction WHERE username=? AND account_type=?::account_type");
			ps.setString(1, username);
			ps.setString(2, accountType.toString());
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
		int transactionID = rs.getInt("transaction_id");
		BigDecimal amount = rs.getBigDecimal("amount");
		String username = rs.getString("username");
		AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
		TransactionType transactionType = TransactionType.valueOf(rs.getString("transaction_type"));
		BankAccount ba = bad.findByUsernameAndType(username, accountType);
		LocalDateTime time = rs.getTimestamp("transaction_time").toLocalDateTime();
		String userTransferredTo = rs.getString("user_transferred_to");
		String accountTypeTransferred = rs.getString("account_type_transferred_to");
		AccountType accountTypeTransferredTo = null;
		BankAccount bankAccountTransferredTo = null;
		if (accountTypeTransferred != null) {
			accountTypeTransferredTo = AccountType.valueOf(accountTypeTransferred);
			bankAccountTransferredTo = bad.findByUsernameAndType(userTransferredTo, accountTypeTransferredTo);
		}
		return new Transaction(transactionID, amount, time, ba, transactionType, bankAccountTransferredTo);
	}

	@Override
	public void updateTransaction(Transaction t) {
		Connection connection = ConnectionFactory.getConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("UPDATE bank_transaction SET username=?, account_type=?::account_type, amount=?, "
							+ "transaction_type=?::transaction_type, transaction_time=?, user_transferred_to=?, "
							+ "account_type_transferred_to=?::account_type");
			ps.setString(1, t.getBankAccount().getUsername());
			ps.setString(2, t.getBankAccount().getAccountType().toString());
			ps.setBigDecimal(3, t.getAmount());
			ps.setString(4, t.getTransactionType().toString());
			ps.setTimestamp(5, Timestamp.valueOf(t.getTime()));
			BankAccount baTransfer = t.getBankAccountTransferredTo();
			String userTransferredTo = null;
			String accountTypeTransferredTo = null;
			if (baTransfer != null) {
				userTransferredTo = baTransfer.getUsername();
				accountTypeTransferredTo = baTransfer.getAccountType().toString();
			}
			ps.setString(6, userTransferredTo);
			ps.setString(7, accountTypeTransferredTo);
			if (ps.executeUpdate() != 1) {
				System.out.println("Error updating transaction.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteTransaction(Transaction t) {
		// TODO Auto-generated method stub

	}

}
