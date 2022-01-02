package com.example.vikard.data.model;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardModel extends SQLDataModel{

    private Integer Id;
    private Integer UsersId;
    private Integer ShopsId;
    private String Barcode;
    private Date ExpiryDate;
    private String UsersCategory;

    //Used to get ShopModel.Name and ShopModel.HexColor
    private ShopModel Shop;

    public CardModel(int id, boolean isFull) {
        Id = id;
        if (isFull) {
            setAll();
        } else {
            setPreview();
        }
    }

    public CardModel(int usersid, int shopsid, String barcode, Date expirydate){
        if (createNewCard(usersid,shopsid,barcode,expirydate)){
            UsersId = usersid;
            ShopsId = shopsid;
            Barcode = barcode;
            ExpiryDate = expirydate;
        }
    }

    //Used for full initialization or supplementing existing CardModels
    @Override
    public void setAll() {
        Connection conn = sql.getConnection();
        try {
            UsersId = Integer.valueOf(getModelDataSQL("UsersId", "Id", "Cards", Id, conn));
            if (ShopsId == null) {
                ShopsId = Integer.valueOf(getModelDataSQL("ShopsId", "Id", "Cards", Id, conn));
                Shop = new ShopModel(ShopsId, true);
            }
            Barcode = getModelDataSQL("Barcode", "Id", "Cards", Id, conn);
            ExpiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(getModelDataSQL("ExpiryDate", "Id", "Cards", Id, conn));
            if (UsersCategory == null) {
                UsersCategory = getModelDataSQL("UsersCategory", "Id", "Cards", Id, conn);
            }
        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    //Used for primary initialization in CardModel List
    @Override
    public void setPreview() {
        Connection conn = sql.getConnection();
        try {
            ShopsId = Integer.valueOf(getModelDataSQL("ShopsId", "Id", "Cards", Id, conn));
            Shop = new ShopModel(ShopsId, false);
            UsersCategory = getModelDataSQL("UsersCategory", "Id", "Cards", Id, conn);
        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    public boolean createNewCard(int usersid, int shopsid, String barcode, Date expirydate){
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        ResultSet resultSet = null;
        try {
            String sqlQuery = "INSERT INTO Cards (UsersId, ShopsId, Barcode, ExpiryDate) VALUES " +
                    "(?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, usersid);
            statement.setInt(2, shopsid);
            statement.setString(3, barcode);
            statement.setDate(4, (java.sql.Date) expirydate);
            statement.executeUpdate();
            sqlQuery = "SELECT Id FROM Cards WHERE Barcode = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, barcode);
            resultSet = statement.executeQuery();
            resultSet.next();
            Id = resultSet.getInt("Id");
            return true;
        } catch (Exception ex) { return false; } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    //region Getters

    public Integer getId() {
        return Id;
    }

    public Integer getUsersId() {
        return UsersId;
    }

    public Integer getShopsId() {
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

    //endregion

    //region Setters

    public void setId(Integer id) {
        Id = id;
    }

    public void setUsersId(Integer usersId) {
        UsersId = usersId;
    }

    public void setShopsId(Integer shopsId) {
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
