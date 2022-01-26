package com.example.vikard.data.model;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class SQLDataModel {

    public SQLConnection sql = new SQLConnection();

    public String getModelDataSQL(String typeTo, String typeFrom, String tableFrom, int idFrom, Connection connection) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT " + typeTo + " FROM " + tableFrom + " WHERE " + typeFrom + "='" + idFrom + "'";
            resultSet = statement.executeQuery(sqlQuery);
            resultSet.next();
            return resultSet.getString(typeTo);
        } catch (Exception ex) {
            return null;
        }
    }

    abstract void setAll();
    abstract void setPreview();

}
