package com.revature.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.User;

public class UserSerializer implements UserDao {

	@Override
	public void createUser(User u) {
		if (u == null) {
			return;
		}
		new File("src/main/resources/users/").mkdirs();
		File f = new File("src/main/resources/users/" + u.getUsername() + ".txt");
		if (f.exists()) {
			return;
		}
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/users/" + u.getUsername() + ".txt"))) {
			oos.writeObject(u);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<User> getAllUsers() {
		File folder = new File("src/main/resources/users/");
		File[] listOfFiles = folder.listFiles();
		List<User> userList = new ArrayList<>();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(listOfFiles[i]))) {
						User u = (User) ois.readObject();
						userList.add(u);
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
		return userList;
	}

	@Override
	public User findByUsername(String username) {
		// verify that what was passed in is not null
		if (username == null) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/" + username + ".txt"))) {
			User u = (User) ois.readObject(); // retrieve the user if it can
			return u;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		// verify that what was passed in is not null
		if (username == null || password == null) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("src/main/resources/users/" + username + ".txt"))) {
			User u = (User) ois.readObject(); // retrieve the user if it can
			// verify that the password matches
			if (password.equals(u.getPassword())) {
				return u;
			} else {
				return null;
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateUser(User u) {
		if (u == null) {
			return;
		}
		File f = new File("src/main/resources/users/" + u.getUsername() + ".txt");
		if (!f.exists()) {
			return;
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/users/" + u.getUsername() + ".txt"))) {

			oos.writeObject(u);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	@Override
	public void deleteUser(User u) {
	}

}