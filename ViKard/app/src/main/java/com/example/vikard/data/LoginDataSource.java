package com.example.vikard.data;

import android.util.Log;

import com.example.vikard.data.model.LoggedInUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    SQLConnection sql = new SQLConnection();
    Connection conn = sql.getConnection();

    public Result<LoggedInUser> login(String username, String password) {
        ResultSet resultSet = null;
        LoggedInUser logUser = new LoggedInUser(username, password);
        try {
            Statement statement = conn.createStatement();
            String sqlQuery = "SELECT Id FROM Users WHERE Email='"+username+"'";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet!= null) {
                resultSet.next();
                int id = resultSet.getInt("Id");
                sqlQuery = "SELECT Password FROM Credentials WHERE UsersId=" + id;
                resultSet = statement.executeQuery(sqlQuery);
                resultSet.next();
                String pwd = resultSet.getString("Password");
                if (pwd.equals(password)){
                    return new Result.Success<>(logUser);
                } else {
                    return new Result.Error(new IOException("The password is incorrect."));
                }
            } else {
                return new Result.Error(new IOException("The user doesn't exist."));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("The user doesn't exist."));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}