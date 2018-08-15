package com.revature.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionFactory {
    public static final String URL = "jdbc:postgresql://revature-1808.c8kcylhnx4kx.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=banking_application";
    public static final String USER = "postgres";
    public static final String PASS = "aws_db_password";

    public static Connection getConnection()
    {
      try {
          DriverManager.registerDriver(new Driver());
          return DriverManager.getConnection(URL, USER, PASS);
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
}