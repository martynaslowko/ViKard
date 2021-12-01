package com.example.vikard.data;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.*;

public class SQLConnection {
    @SuppressLint("NewApi")
    public static Connection getConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection conn = null;
        String connURL = "jdbc:mariadb://orangecraft.atthost24.pl:3306/6597_ViKard?user=6597_ViKard&password=IPZ#2021";
        try {
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(connURL);
            return conn;
        } catch (Exception e){
            return null;
        }
    }
}
