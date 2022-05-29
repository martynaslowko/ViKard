package com.example.vikard.ui.LoginRegister;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDataSource {

    public void executeRegisterQuery(String name, String lastName,
                                     String email, String birthDate,
                                     String password)
    {
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        ResultSet resultSet = null;
        try {
            String sqlQuery = "INSERT INTO Users (Name, LastName, Email, BirthDate) VALUES " +
                    "(?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setDate(4, java.sql.Date.valueOf(birthDate));
            statement.executeUpdate();
            sqlQuery = "CALL getSaltPwd(?)";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, password);
            resultSet = statement.executeQuery();
            resultSet.next();
            String saltpwd = resultSet.getString("saltpwd");
            sqlQuery = "SELECT Id FROM Users WHERE Email = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            resultSet.next();
            sqlQuery = "INSERT INTO Credentials (UsersId, Password) VALUES (?, ?)";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, resultSet.getInt("Id"));
            statement.setString(2, saltpwd);
            statement.executeUpdate();
        } catch (Exception ex) { } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

}
