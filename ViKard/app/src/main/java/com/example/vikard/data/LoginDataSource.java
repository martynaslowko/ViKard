package com.example.vikard.data;

import com.example.vikard.ViKard;
import com.example.vikard.data.model.LoggedInUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    SQLConnection sql = new SQLConnection();
    Connection conn = sql.getConnection();

    public Result<LoggedInUser> login(String username, String password) {
        ResultSet resultSet = null;
//        boolean flag = ((ViKard) this.getApplication()).getSomeVariable();
        try {
            String sqlQuery = "SELECT Id FROM Users WHERE Email = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet!= null) {
                resultSet.next();
                int id = resultSet.getInt("Id");
                sqlQuery = "SELECT Password FROM Credentials WHERE UsersId = ?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                resultSet.next();
                String pwd = resultSet.getString("Password");
                sqlQuery = "CALL getSaltPwd(?)";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, password);
                resultSet = statement.executeQuery();
                resultSet.next();
                String saltpwd = resultSet.getString("saltpwd");
                if (pwd.equals(saltpwd)){
                    LoggedInUser logUser = new LoggedInUser(String.valueOf(id), username);
                    return new Result.Success<>(logUser);
                } else {
                    return new Result.Error(new IOException("The password is incorrect."));
                }
            } else {
                return new Result.Error(new IOException("The user doesn't exist."));
            }
        } catch (Exception ex) {
            return new Result.Error(new IOException("The user doesn't exist."));
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }

    }

    public Result<LoggedInUser> shop_login(String username, String password) {
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT Id FROM Shops WHERE Email = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet!= null) {
                resultSet.next();
                int id = resultSet.getInt("Id");
                sqlQuery = "SELECT Password FROM ShopsCredentials WHERE ShopsId = ?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                resultSet.next();
                String pwd = resultSet.getString("Password");
                if (pwd.equals(password)){
                    LoggedInUser logUser = new LoggedInUser(String.valueOf(id), username);
                    return new Result.Success<>(logUser);
                } else {
                    return new Result.Error(new IOException("The password is incorrect."));
                }
            } else {
                return new Result.Error(new IOException("The user doesn't exist."));
            }
        } catch (Exception ex) {
            return new Result.Error(new IOException("The user doesn't exist."));
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}