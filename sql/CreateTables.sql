set schema 'banking_application';
CREATE TABLE bank_users
(
	username VARCHAR(32) NOT NULL PRIMARY KEY,
	password VARCHAR(50) NOT NULL,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	age INTEGER NOT NULL,
	admin_level INTEGER NOT NULL
);

CREATE TYPE account_type AS ENUM ('Checking', 'Savings');

CREATE TABLE bank_accounts
(
    username VARCHAR(32) NOT NULL REFERENCES bank_users(username),
	account_type account_type NOT NULL,
	balance NUMERIC(12, 2) NOT NULL,
	PRIMARY KEY(username, account_type)
);
CREATE TABLE shared_users_accounts
(
    username VARCHAR(32) NOT NULL,
	account_type account_type NOT NULL,
	user_shared_with VARCHAR(32) NOT NULL REFERENCES bank_users(username),
	FOREIGN KEY (username, account_type) REFERENCES bank_accounts(username, account_type),
	PRIMARY KEY(username, account_type, user_shared_with)
);
CREATE TYPE transaction_type AS ENUM ('Withdrawal', 'Deposit', 'Wire Transfer');
CREATE TABLE bank_transactions
(
    username VARCHAR(32) NOT NULL,
	account_type account_type NOT NULL,
	amount NUMERIC(12, 2) NOT NULL,
	transaction_type transaction_type NOT NULL,
	transaction_time TIME NOT NULL,
	user_transferred_to VARCHAR(32),
	account_type_transferred_to account_type,
	FOREIGN KEY (username, account_type) REFERENCES bank_accounts(username, account_type),
	FOREIGN KEY (user_transferred_to, account_type_transferred_to) REFERENCES bank_accounts(username, account_type)
);