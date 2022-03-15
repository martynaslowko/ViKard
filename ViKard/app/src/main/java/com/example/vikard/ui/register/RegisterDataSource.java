package com.example.vikard.ui.register;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDataSource {

    SQLConnection sql = new SQLConnection();

    public boolean checkRegisteredEmail (String email){
        Connection conn = sql.getConnection();
        ResultSet resultSet;
        try {
            String sqlQuery = "SELECT id FROM Users WHERE Email=?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next() == false) return true;
            else return false;
        } catch (Exception ex) { return false; } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    public void executeRegisterQuery(String email, String password,
                                     String name, String lastName,
                                     String birthDate)
    {
        Connection conn = sql.getConnection();
        ResultSet resultSet;
        try {
            String sqlQuery = "INSERT INTO Users (Name, LastName, Email, BirthDate) VALUES " +
                    "(?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setDate(4, java.sql.Date.valueOf(birthDate));
            statement.executeUpdate();
            sqlQuery = "SELECT Id FROM Users WHERE Email = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            resultSet.next();
            sqlQuery = "INSERT INTO Credentials (UsersId, Password) VALUES (?, ?)";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, resultSet.getInt("Id"));
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (Exception ex) { } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

}
