package com.example.vikard.data;

import java.sql.*;

public class SQLConnection{
    Connection conn = getConnection();

    public Connection getConnection(){
        try {
        Class.forName("org.mariadb.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mariadb://orangecraft.atthost24.pl:3306/6597_ViKard?user=6597_ViKard&password=IPZ#2021");
            return connection;
        } catch (Exception e) {
            return null;
        }
    }
}
