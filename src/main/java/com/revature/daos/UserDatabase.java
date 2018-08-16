package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import com.revature.beans.BankAccount.AccountType;
import com.revature.util.ConnectionUtil;
import com.revature.beans.User;

public class UserDatabase implements UserDao {
	private ConnectionUtil cu = ConnectionUtil.cu;

	@Override
	public void createUser(User u) {
		Connection conn = cu.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO bank_user(username, password, first_name, last_name, age, admin_level) VALUES (?, ?, ?, ?, ?, ?)");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setInt(5, u.getAge());
			ps.setInt(6, u.getAdminLevel());
			if (ps.executeUpdate() != 1) {
				System.out.println("Error updating user.");
			}
			ps.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public User findByUsername(String username) {
		Connection conn = cu.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM bank_user WHERE username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return extractUserFromResultSet(rs);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		Connection conn = cu.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM bank_user WHERE username=? AND password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return extractUserFromResultSet(rs);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		Connection conn = cu.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM bank_user");
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				User user = extractUserFromResultSet(rs);
				users.add(user);
			}
			rs.close();
			conn.close();
			return users;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private User extractUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setAge(rs.getInt("age"));
		user.setAdminLevel(rs.getInt("admin_level"));
		user.setSharedAccounts(findSharedAccounts(user));
		return user;
	}

	private Set<Entry<String, AccountType>> findSharedAccounts(User u) throws SQLException {
		Set<Entry<String, AccountType>> sharedAccounts = new HashSet<>();
		Connection conn = cu.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM shared_users_accounts WHERE user_shared_with=?");
		ps.setString(1, u.getUsername());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String accountOwner = rs.getString("username");
			AccountType accountType = AccountType.valueOf(rs.getString("account_type"));
			sharedAccounts.add(new SimpleEntry<>(accountOwner, accountType));
		}
		rs.close();
		ps.close();
		conn.close();
		return sharedAccounts;
	}

	@Override
	public void updateUser(User u) {
		Connection conn = cu.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE bank_user SET password=?, first_name=?, last_name=?, age=?, admin_level=? WHERE username=?");
			ps.setString(1, u.getPassword());
			ps.setString(2, u.getFirstName());
			ps.setString(3, u.getLastName());
			ps.setInt(4, u.getAge());
			ps.setInt(5, u.getAdminLevel());
			ps.setString(6, u.getUsername());
			if (ps.executeUpdate() == 1) {
				createSharedBankAccounts(u);
			} else {
				System.out.println("Error updating user.");
			}
			ps.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void createSharedBankAccounts(User u) throws SQLException {
		Connection conn = cu.getConnection();
		Set<Entry<String, AccountType>> savedSharedAccounts = findSharedAccounts(u);
		Set<Entry<String, AccountType>> sharedAccounts = new HashSet<>(u.getSharedAccounts());
		sharedAccounts.removeAll(savedSharedAccounts);
		for (Entry<String, AccountType> sharedAccount : sharedAccounts) {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO shared_users_accounts(username, account_type, "
					+ "user_shared_with) VALUES (?, ?::account_type, ?) ON CONFLICT DO NOTHING");
			ps.setString(1, sharedAccount.getKey());
			ps.setString(2, sharedAccount.getValue().toString());
			ps.setString(3, u.getUsername());
			if (ps.executeUpdate() != 1) {
				System.out.println("Error creating shared bank account.");
			}
			ps.close();
		}
		conn.close();
	}

	@Override
	public void deleteUser(User u) {
		// TODO Auto-generated method stub

	}

}
