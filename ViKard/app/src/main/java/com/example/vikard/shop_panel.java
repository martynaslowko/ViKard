package com.example.vikard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.model.ShopModel;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class shop_panel extends AppCompatActivity {
    private PieChart pieChart;
    private String shop_name = "";
    private int shopid;
    private int shop_card_count = 0;
    private int card_count = 0;
    private String HexColor;

    TextView name;
    ImageView background;
    View colorbox;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        shopid = Integer.valueOf(LoginRepository.user.getUserId());

        //Get data
        setAll();
        shop_card_count = getShopsStats(shopid,true);
        card_count = getShopsStats(shopid,false);

        //Set shop name
        name = (TextView) findViewById(R.id.shop_name_title);
        name.setText(shop_name);

        //Change background color and pieChart legend colorbox
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getBaseContext(), R.drawable.color_box);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#"+HexColor));

        colorbox.setBackgroundColor(Color.parseColor("#"+HexColor));

        setContentView(R.layout.activity_shop_panel);
        pieChart = findViewById(R.id.piechart);
        int percent = (shop_card_count/card_count)*100;
        setData(percent);
    }

    private void setData(int percent)
    {
        pieChart.addPieSlice(
                new PieModel(
                        "Our shop ("+percent+")",
                        percent,
                        Color.parseColor("#"+HexColor)));
        pieChart.addPieSlice(
                new PieModel(
                        "Others ("+(100-percent)+")",
                        100-percent,
                        Color.parseColor("#66BB6A")));
        pieChart.startAnimation();
    }

    public void setAll() {
        ShopModel temp = new ShopModel(shopid,false);
        shop_name = temp.getName();
        HexColor = temp.getHexColor();
    }

    private int getShopsStats(int id, boolean isSpecific){
        try
        {
            SQLConnection sql = new SQLConnection();
            Connection conn = sql.getConnection();
            String sqlQuery = "SELECT count(*) FROM Cards";
            if (isSpecific){
                sqlQuery += " WHERE ShopsId = ?";
            }
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            if (isSpecific){
                statement.setInt(1, id);
            }
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("count(*)");
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}