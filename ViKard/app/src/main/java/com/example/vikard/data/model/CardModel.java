package com.example.vikard.data.model;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardModel extends SQLDataModel{

    private Integer Id;
    private Integer UsersId = Integer.valueOf(LoginRepository.user.getUserId());
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

    //New Card constructor, can be added to CardCollection later and populated on selection.
    public CardModel(String shopsname, String barcode, Date expirydate){
        if (createNewCard(shopsname,barcode,expirydate)){
            Shop = new ShopModel(ShopsId, false);
            Barcode = barcode;
            ExpiryDate = expirydate;
        } else {
            ShopsId = null;
            UsersId = null;
            Id = null;
        }
    }

    //Used for full initialization or supplementing existing CardModels
    @Override
    public void setAll() {
        Connection conn = sql.getConnection();
        try {
            if (ShopsId == null) {
                Shop = new ShopModel(Integer.valueOf(getModelDataSQL("ShopsId", "Id", "Cards", Id, conn)), true);
                ShopsId = Shop.getId();
            } else {
                Shop.setAll();
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
            Shop = new ShopModel(Integer.valueOf(getModelDataSQL("ShopsId", "Id", "Cards", Id, conn)), false);
            ShopsId = Shop.getId();
            UsersCategory = getModelDataSQL("UsersCategory", "Id", "Cards", Id, conn);
        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    //Used for creating a new card
    public boolean createNewCard(String shopsname, String barcode, Date expirydate){
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        ResultSet resultSet = null;
        try {
            //get ShopsId
            String sqlQuery = "SELECT Id FROM Shops WHERE Name = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, shopsname);
            resultSet = statement.executeQuery();
            resultSet.next();
            ShopsId = resultSet.getInt("Id");
            //Insert new Card
            sqlQuery = "INSERT INTO Cards (UsersId, ShopsId, Barcode, ExpiryDate) VALUES " +
                    "(?, ?, ?, ?)";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, UsersId);
            statement.setInt(2, ShopsId);
            statement.setString(3, barcode);
            statement.setDate(4, (java.sql.Date) expirydate);
            statement.executeUpdate();
            //get new Card's Id
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
