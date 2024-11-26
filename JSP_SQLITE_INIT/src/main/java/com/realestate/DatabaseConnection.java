package com.realestate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if ( connection == null || connection.isClosed() ) {
            try {
                // Load the SQLite JDBC driver (optional, depending on your setup)
                Class.forName("org.sqlite.JDBC");

                // Create a new connection (use absolute path if needed)
                connection = DriverManager.getConnection("jdbc:sqlite:localdb.db");
                initDB(connection);
            } catch (ClassNotFoundException e) {
                System.err.println("SQLite JDBC Driver not found.");
                e.printStackTrace();
            }
        }

        return connection;
    }


    public static void initDB(Connection con) {
        try {
            Statement st = con.createStatement();
            st.execute("PRAGMA foreign_keys = ON;");
            String createUserTable = "CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL, role TEXT DEFAULT 'user');";
            st.executeUpdate(createUserTable);

            String createBookTable = "CREATE TABLE IF NOT EXISTS book(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, genre TEXT NOT NULL, available_quantity INTEGER NOT NULL);";
            st.executeUpdate(createBookTable);

            String createTransactionTable = "CREATE TABLE IF NOT EXISTS transactions(id INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, bookID INTEGER, action TEXT CHECK(action IN ('BORROW', 'RETURN', 'RENEW')) NOT NULL, date DATE DEFAULT CURRENT_DATE, FOREIGN KEY (userID) REFERENCES user(id), FOREIGN KEY (bookID) REFERENCES book(id));";
            st.executeUpdate(createTransactionTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}