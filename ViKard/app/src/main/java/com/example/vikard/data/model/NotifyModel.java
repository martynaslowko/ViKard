package com.example.vikard.data.model;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotifyModel extends SQLDataModel{

    private Integer Id;
    private Date DateTime;
    private String Title;
    private String Content;

    //Used to get ShopModel.Name and set/get ShopModel.Id
    private ShopModel Shop;

    public NotifyModel(int id) {
        Id = id;
        setAll();
    }

    @Override
    void setAll() {
        Connection conn = sql.getConnection();
        try {
            Shop = new ShopModel(Integer.valueOf(getModelDataSQL("ShopsId", "Id", "Notifications", Id, conn)), false);
            DateTime = new SimpleDateFormat("yyyy-MM-dd").parse(getModelDataSQL("DateTime", "Id", "Notifications", Id, conn));
            Title = getModelDataSQL("Title", "Id", "Notifications", Id, conn);
            Content = getModelDataSQL("Content", "Id", "Notifications", Id, conn);
        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    @Override
    void setPreview(){}

    //region Getters

    public Integer getId() {
        return Id;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    //endregion

    //region Setters

    public void setId(Integer id) {
        Id = id;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setContent(String content) {
        Content = content;
    }

    //endregion
}
