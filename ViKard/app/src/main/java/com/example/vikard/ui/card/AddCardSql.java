package com.example.vikard.ui.card;

import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.example.vikard.CardList;
public class AddCardSql {

    public void executeAddQuery(int userId, int ShopID, String barcode, String ExpiryDate, String UserCategory)
    {

        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        ResultSet resultSet = null;

        try {
            String sqlQuery = "INSERT INTO Cards (UsersId, ShopsId, Barcode, ExpiryDate, UsersCategory) VALUES " +
                    "(?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, userId);
            statement.setInt(2, ShopID);
            statement.setString(3, barcode);
            statement.setDate(4, java.sql.Date.valueOf(ExpiryDate));
            statement.setString(5, UserCategory);
            statement.executeUpdate();

        } catch (Exception ex) { } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }
}
