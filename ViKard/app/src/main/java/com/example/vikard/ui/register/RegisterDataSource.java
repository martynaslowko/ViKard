package com.example.vikard.ui.register;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterDataSource {

    public void executeRegisterQuery(String name, String lastName, String email, String birthDate, String password){
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        ResultSet resultSet = null;
        try {
            Statement statement = ((Connection) conn).createStatement();
            String sqlQuery = "INSERT INTO Users (Name, LastName, Email, BirthDate) VALUES " +
                    "('"+name+"', '"+lastName+"', '"+email+"', '"+birthDate+"')";
            statement.executeUpdate(sqlQuery);
            sqlQuery = "SELECT Id FROM Users WHERE Email='"+email+"'";
            resultSet = statement.executeQuery(sqlQuery);
            resultSet.next();
            sqlQuery = "INSERT INTO Credentials (UsersId, Password) VALUES " +
                    "("+resultSet.getInt("Id")+", '"+password+"')";
            statement.executeUpdate(sqlQuery);
        } catch (Exception ex) {
            return;
        }
    }

}
