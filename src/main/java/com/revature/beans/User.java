package com.revature.beans;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.revature.beans.BankAccount.AccountType;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2878891003003024330L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int adminLevel;
	private int age;
	private static User currentUser;
	private Set<Entry<String, AccountType>> sharedAccounts;

	public User(String username, String password, String firstName, String lastName, int age) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.adminLevel = 0;
		sharedAccounts = new HashSet<>();
	}

	public User() {
		super();
		this.adminLevel = 0;
		sharedAccounts = new HashSet<>();
	}

	public static void setCurrentUser(User currentUser) {
		User.currentUser = currentUser;
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public boolean isAdmin() {
		return adminLevel > 0;
	}

	public int getAdminLevel() {
		return adminLevel;
	}

	public void setAdminLevel(int adminLevel) {
		this.adminLevel = adminLevel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Entry<String, AccountType>> getSharedAccounts() {
		return sharedAccounts;
	}

	public boolean addSharedAccount(String username, AccountType accountType) {
		return sharedAccounts.add(new SimpleEntry<>(username, accountType));
	}
	
	public void setSharedAccounts(Set<Entry<String, AccountType>> sharedAccounts) {
		this.sharedAccounts = sharedAccounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (age != other.age)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", age=" + age + "]";
	}

}