package com.example.racketready;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    // JdbcTemplate template;

//    public DatabaseHelper(JdbcTemplate template) {
//        this.template = template;
//    }

    protected static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {

        // Initialize all the information regarding
        // Database Connection
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost/RacketReady";
        // Database name to access
        String dbName = "RacketReadyDB";
        String dbUsername = "root";
        String dbPassword = "root";

        Class.forName(dbDriver);
        Connection con = DriverManager.getConnection(dbURL + dbName,
                dbUsername,
                dbPassword);
        return con;

    }

}

