package com.example.vikard.data.model;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardModel {

    private int Id;
    private int UsersId;
    private int ShopsId;
    private String Barcode;
    private Date ExpiryDate;
    private String UsersCategory;

    public CardModel(int id) {
        Id = id;
        setAll();
    }

    public void setAll() {
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        try {
            UsersId = Integer.valueOf(getCardDataSQL("UsersId", conn));
            ShopsId = Integer.valueOf(getCardDataSQL("ShopsId", conn));
            Barcode = getCardDataSQL("Barcode", conn);
            ExpiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(getCardDataSQL("ExpiryDate", conn));
            UsersCategory = getCardDataSQL("UsersCategory", conn);
        } catch (Exception ex) {
            return;
        }
    }

    public String getCardDataSQL(String type, Connection connection) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT " + type + " FROM Cards WHERE Id=" + Id;
            resultSet = statement.executeQuery(sqlQuery);
            resultSet.next();
            return resultSet.getString(type);
        } catch (Exception ex) {
            return null;
        }
    }

    //region Getters

    public int getId() {
        return Id;
    }

    public int getUsersId() {
        return UsersId;
    }

    public int getShopsId() {
        return ShopsId;
    }

    public String getBarcode() {
        return Barcode;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public String getUsersCategory() {
        return UsersCategory;
    }

    public void setId(int id) {
        Id = id;
    }

    //endregion

    //region Setters

    public void setUsersId(int usersId) {
        UsersId = usersId;
    }

    public void setShopsId(int shopsId) {
        ShopsId = shopsId;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public void setExpiryDate(Date expiryDate) {
        ExpiryDate = expiryDate;
    }

    public void setUsersCategory(String usersCategory) {
        UsersCategory = usersCategory;
    }

    //endregion
}
