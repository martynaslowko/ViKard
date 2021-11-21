package com.example.vikard.data;

import java.sql.*;

public class SQLConnection{
    public static String connectionString =
            "jdbc:sqlserver://vikardapp.database.windows.net;"
            + "database=vikard;"
            + "user=vikarddbroot@vikardapp;"
            + "password=M37^ajnc*1;"
            + "encrypt=true;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;";

    Connection conn = getConnection();

    public Connection getConnection(){
        try (Connection connection = DriverManager.getConnection(connectionString)){
            return connection;
        } catch (Exception e) {
            return null;
        }
    }
}
