package com.example.vikard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class shop_panel extends AppCompatActivity {
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_shop_panel);
        pieChart = findViewById(R.id.piechart);
        setData(69);
    }

    private void setData(int percent)
    {
        pieChart.addPieSlice(
                new PieModel(
                        "Our shop ("+percent+")",
                        percent,
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Others ("+(100-percent)+")",
                        100-percent,
                        Color.parseColor("#66BB6A")));
        pieChart.startAnimation();
    }
}