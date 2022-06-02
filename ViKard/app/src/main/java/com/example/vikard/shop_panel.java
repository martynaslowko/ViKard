package com.example.vikard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.model.ShopModel;
import com.example.vikard.ui.LoginRegister.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class shop_panel extends AppCompatActivity {
    private PieChart pieChart;
    private String shop_name = "";
    private int shopid, shop_card_count = 0, card_count = 0;
    private String HexColor, API;


    private TextView name, cards_count, little_name, little_others;
    private EditText hex, api;
    private ImageView logout;
    private View colorbox;
    private Button save_color, save_api;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_shop_panel);


        //Get data
        shopid = Integer.valueOf(LoginRepository.user.getUserId());
        setAll();
        getAPI();
        shop_card_count = getShopsStats(shopid,true);
        card_count = getShopsStats(shopid,false);


        //Set shop name
        name = (TextView) findViewById(R.id.shop_name_title);
        name.setText(shop_name);
        little_others = (TextView) findViewById(R.id.little_others);
        little_name = (TextView) findViewById(R.id.little_name);
        little_name.setText(shop_name);

        //Change background color and pieChart legend colorbox
        colorbox = findViewById(R.id.imageView2);
        colorbox.setBackgroundColor(Color.parseColor("#"+HexColor));
        colorbox = findViewById(R.id.color_box);
        colorbox.setBackgroundColor(Color.parseColor("#"+HexColor));

        //Change texts
        cards_count = (TextView) findViewById(R.id.cards_count);
        cards_count.setText(Integer.toString(shop_card_count));

        //Logout button
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(shop_panel.this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(shop_panel.this, HomeActivity.class));
                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();



            }
        });

        //Piechart
        pieChart = findViewById(R.id.piechart);
        float percent = ((float)shop_card_count/(float)card_count)*100;
        setData(percent);
        Log.i("h "+Float.toString(percent),Integer.toString(card_count));
        little_name.setText(shop_name + " ("+(int)percent + "%)");
        little_others.setText("Others ("+(int)(100-(int)percent) + "%)");
        //Save buttons
        save_color = findViewById(R.id.save_color);
        save_api = findViewById(R.id.save_api);
        save_color.setEnabled(false);
        save_api.setEnabled(false);

        save_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_color();
            }
        });
        save_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               update_api();
            }
        });

        //Edittexts
        hex = findViewById(R.id.color_edit);
        api = findViewById(R.id.api_edit);

        hex.setText(HexColor);
        api.setText(API);

        hex.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if ((hex.getText().toString()).length() == 6){
                    save_color.setEnabled(true);
                }
                else{
                    save_color.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        api.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if ((api.getText().toString()).length() != 0){
                    save_api.setEnabled(true);
                }
                else{
                    save_api.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void setData(float percent)
    {
        pieChart.addPieSlice(
                new PieModel(
                        shop_name,
                        percent,
                        Color.parseColor("#"+HexColor)));
        pieChart.addPieSlice(
                new PieModel(
                        "Others",
                        100-percent,
                        Color.parseColor("#7a7c80")));
        pieChart.startAnimation();
    }

    public void setAll() {
        ShopModel temp = new ShopModel(shopid,false);
        shop_name = temp.getName();
        HexColor = temp.getHexColor();
        Log.i(shop_name, HexColor);
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

    private void getAPI(){
        try
        {
            SQLConnection sql = new SQLConnection();
            Connection conn = sql.getConnection();
            String sqlQuery = "SELECT API FROM Shops WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, shopid);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            API = resultSet.getString("API");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void update_color(){
        String color = hex.getText().toString();
        Log.i("h Api", color);

       try{
            //Test czy to kolor
            Color.parseColor("#"+color);

            //Połączenie z bazą
            SQLConnection sql = new SQLConnection();
            Connection conn = sql.getConnection();

            //Zapytanie
            String sqlQuery = "UPDATE Shops SET HexColor = ? WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, color);
            statement.setInt(2, shopid);
            statement.executeQuery();
            finish();
            startActivity(getIntent());
       } catch (SQLException throwables) {
            throwables.printStackTrace();
       }
    }

    private void update_api(){
        String api_text = api.getText().toString();
        Log.i("h Api", api_text);
        try{
            //Połączenie z bazą
            SQLConnection sql = new SQLConnection();
            Connection conn = sql.getConnection();

            //Zapytanie
            String sqlQuery = "UPDATE Shops SET API = ? WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, api_text);
            statement.setInt(2, shopid);
            statement.executeQuery();
            finish();
            startActivity(getIntent());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}