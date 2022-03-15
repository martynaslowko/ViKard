package com.example.vikard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.vikard.data.model.CardModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Karta extends AppCompatActivity {
    String shopName, expireDate, barcode, hexColor,link;
    TextView shopName_, expireDate_, barcode_, barcodeText;
    ImageView boxBarcode;
    Button linkbutton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        if(b != null) {
            hexColor = b.getString("hexColor");
            shopName = b.getString("shopName");
            expireDate = b.getString("expireDate");
            barcode = b.getString("barcode");
            link = b.getString("link");
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getBaseContext(), R.drawable.card);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#"+hexColor));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.karta);

        shopName_ = (TextView) findViewById(R.id.shopName);
        expireDate_ = (TextView) findViewById(R.id.cardExpires);
        barcodeText = (TextView)findViewById(R.id.barcode_text);
        barcode_ = (TextView) findViewById(R.id.barcode);
        shopName_.setText(shopName);
        expireDate_.setText(expireDate);
        barcode_.setText(barcode);
        barcodeText.setText(barcode);
        boxBarcode = findViewById(R.id.imageView2);
        linkbutton = findViewById(R.id.linkbutton);
        barcode_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expireDate_.setVisibility(View.INVISIBLE);
                barcode_.setVisibility(View.INVISIBLE);
                barcodeText.setVisibility(View.INVISIBLE);
                boxBarcode.setVisibility(View.INVISIBLE);
                linkbutton.setVisibility(View.VISIBLE);
            }
        });

        linkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(link);
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
