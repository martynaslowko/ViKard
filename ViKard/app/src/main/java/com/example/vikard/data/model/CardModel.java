package com.example.vikard.data.model;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardModel {

    private SQLConnection sql = new SQLConnection();
    private Integer Id;
    private Integer UsersId;
    private Integer ShopsId;
    private String Barcode;
    private Date ExpiryDate;
    private String UsersCategory;

    private String ShopsName;

    public CardModel(int id, boolean isFull) {
        Id = id;
        if (isFull) {
            setAll();
        } else {
            setPreview();
        }
    }

    //Used for full initialization or supplementing existing CardModels
    public void setAll() {
        Connection conn = sql.getConnection();
        try {
            UsersId = Integer.valueOf(getCardDataSQL("UsersId", "Id", "Cards", Id, conn));
            if (ShopsId == null) {
                ShopsId = Integer.valueOf(getCardDataSQL("ShopsId", "Id", "Cards", Id, conn));
            }
            Barcode = getCardDataSQL("Barcode", "Id", "Cards", Id, conn);
            ExpiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(getCardDataSQL("ExpiryDate", "Id", "Cards", Id, conn));
            if (UsersCategory == null) {
                UsersCategory = getCardDataSQL("UsersCategory", "Id", "Cards", Id, conn);
            }
            if (ShopsName == null) {
                ShopsName = getCardDataSQL("Name", "Id", "Shops", ShopsId, conn);
            }
        } catch (Exception ex) {
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    //Used for primary initialization in CardModel List
    public void setPreview() {
        Connection conn = sql.getConnection();
        try {
            ShopsId = Integer.valueOf(getCardDataSQL("ShopsId", "Id", "Cards", Id, conn));
            UsersCategory = getCardDataSQL("UsersCategory", "Id", "Cards", Id, conn);
            ShopsName = getCardDataSQL("Name", "Id", "Shops", ShopsId, conn);
        } catch (Exception ex) {
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public String getCardDataSQL(String typeTo, String typeFrom, String tableFrom, int idFrom, Connection connection) {
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

    public String getShopsName() {
        return ShopsName;
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

    public void setShopsName(String shopsName) {
        ShopsName = shopsName;
    }

    //endregion
}
