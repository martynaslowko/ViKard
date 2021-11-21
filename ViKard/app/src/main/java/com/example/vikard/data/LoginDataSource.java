package com.example.vikard.data;

import android.util.Log;

import com.example.vikard.data.model.LoggedInUser;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    SQLConnection sql = new SQLConnection();

    public Result<LoggedInUser> login(String username, String password) {
        ResultSet resultSet = null;
        LoggedInUser logUser = new LoggedInUser(username, password);
        try {
            Statement statement = sql.conn.createStatement();
            String sqlQuery = "SELECT Id FROM dbo.Users WHERE E-mail='"+username+"'";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet != null) {
                sqlQuery = "SELECT password FROM dbo.Credentials WHERE Id='"+resultSet+"'";
                resultSet = statement.executeQuery(sqlQuery);
                if (resultSet.toString() == password){
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

/*        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }*/
    }

    public void logout() {
        // TODO: revoke authentication
    }
}