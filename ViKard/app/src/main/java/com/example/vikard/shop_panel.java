package com.example.vikard;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class shop_panel extends AppCompatActivity {
    TextView shop_name_title, shop_name_piechart;
    PieChart pieChart;
    String shop_name;
    String HexColor;

//    private ActivityShopPanelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shop_name_title = findViewById(R.id.shop_name_title);
        shop_name_piechart = findViewById(R.id.shop_name_piechart);
        pieChart = findViewById(R.id.piechart);
        setData();
    }

    private void setData()
    {
        float percent = 0;
        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        shop_name,
                        percent,
                        Color.parseColor(HexColor)));
        pieChart.addPieSlice(
                new PieModel(
                        "Others",
                        100-percent,
                        Color.parseColor("@android:color/secondary_text_light")));
        // To animate the pie chart
        pieChart.startAnimation();
    }
}