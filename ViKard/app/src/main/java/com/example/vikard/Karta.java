package com.example.vikard;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

public class Karta extends AppCompatActivity {
    String shopName;
    String expireDate;
    String barcode;
    String hexColor;
    TextView shopName_;
    TextView expireDate_;
    TextView barcode_;
    TextView barcode2_;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        if(b != null) {
            hexColor = b.getString("hexColor");
            shopName = b.getString("shopName");
            expireDate = b.getString("expireDate");
            barcode = b.getString("barcode");
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getBaseContext(), R.drawable.card);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#"+hexColor));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.karta);

        shopName_ = (TextView) findViewById(R.id.shopName);
        expireDate_ = (TextView) findViewById(R.id.cardExpires);
        barcode_ = (TextView) findViewById(R.id.barcode);
        barcode2_ = (TextView) findViewById(R.id.barcode);
        shopName_.setText(shopName);
        expireDate_.setText(expireDate);
        barcode_.setText(barcode);
    }
}
