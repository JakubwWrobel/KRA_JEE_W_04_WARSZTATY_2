package com.github.JakubwWrobel.addin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {

    private static String DB_URL = "jdbc:mysql://localhost:3306/szkola_programowania?useSSL=false&characterEncoding=utf8";
    private static String DB_USER = "root";
    private static String DB_PASS = "coderslab";

    public static Connection getConnection()throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
        return connection;
    }
}
