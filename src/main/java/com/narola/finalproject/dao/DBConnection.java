package com.narola.finalproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection = null;
    private Connection conn = null;

    private DBConnection() {
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinefooddelivery", "root", "");
                System.out.println("database connect");

            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("not connect");
                e.printStackTrace();
            }
        }
        return conn;
    }
}

