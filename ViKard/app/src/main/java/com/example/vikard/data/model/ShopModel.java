package com.example.vikard.data.model;

import java.sql.Connection;

public class ShopModel extends SQLDataModel{

    private Integer Id;
    private String Name;
    private String LoyaltyType;
    private String HomeLink;
    private String HexColor;

    public ShopModel(int id, boolean isFull) {
        Id = id;
        if (isFull) {
            setAll();
        } else {
            setPreview();
        }
    }

    //Used for full initialization or supplementing existing ShopModels
    @Override
    public void setAll() {
        Connection conn = sql.getConnection();
        try {
            if (Name == null) {
                Name = getModelDataSQL("Name", "Id", "Shops", Id, conn);
            }
            LoyaltyType = getModelDataSQL("LoyaltyType", "Id", "Shops", Id, conn);
            HomeLink = getModelDataSQL("HomeLink", "Id", "Shops", Id, conn);
            if (HexColor == null) {
                HexColor = getModelDataSQL("HexColor", "Id", "Shops", Id, conn);
            }
        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    //Used for primary initialization in ShopModel List
    @Override
    public void setPreview() {
        Connection conn = sql.getConnection();
        try {
            Name = getModelDataSQL("Name", "Id", "Shops", Id, conn);
            HexColor = getModelDataSQL("HexColor", "Id", "Shops", Id, conn);
        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    //region Getters

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getLoyaltyType() {
        return LoyaltyType;
    }

    public String getHomeLink() {
        return HomeLink;
    }

    public String getHexColor() {
        return HexColor;
    }

    //endregion

    //region Setters

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLoyaltyType(String loyaltyType) {
        LoyaltyType = loyaltyType;
    }

    public void setHomeLink(String homeLink) {
        HomeLink = homeLink;
    }

    public void setHexColor(String hexColor) {
        HexColor = hexColor;
    }

    //endregion
}
