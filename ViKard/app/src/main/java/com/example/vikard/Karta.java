package com.example.vikard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Karta extends AppCompatActivity {


    String CardShopNameString, CardExpireDateString, CardBarcodeString, hexColor, CardLinkString, PointsString;

    TextView ShopNameTextView, ExpiryDateTextView, BarcodeTextView, PointsTextView, PointsLabelView;

    ImageView BoxBarcodeImageView, BackButtonImageView, BarcodeImageView;

    Button LinkButton;


    private void generateBarcodeImage()
    {
        String p_barcodeText= BarcodeTextView.getText().toString(); // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(p_barcodeText, BarcodeFormat.CODE_128,400,40);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            BarcodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            hexColor = b.getString("hexColor");
            CardShopNameString = b.getString("shopName");
            CardExpireDateString = b.getString("expireDate");
            CardBarcodeString = b.getString("barcode");
            CardLinkString = b.getString("link");
            PointsString = b.getString("points");
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getBaseContext(), R.drawable.card);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#"+hexColor));

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.karta);


        ShopNameTextView = (TextView) findViewById(R.id.ShopNameText);
        ExpiryDateTextView = (TextView) findViewById(R.id.CardExpiryDateText);
        BarcodeTextView = (TextView)findViewById(R.id.BarcodeText);
        BarcodeImageView = (ImageView) findViewById(R.id.BarcodeImageView);
        PointsLabelView = (TextView) findViewById(R.id.PointsLabel);
        PointsTextView = (TextView) findViewById(R.id.PointsText);

        ShopNameTextView.setText(CardShopNameString);
        ExpiryDateTextView.setText(CardExpireDateString);
        BarcodeTextView.setText(CardBarcodeString);
        PointsTextView.setText(PointsString);

        BoxBarcodeImageView = findViewById(R.id.WhiteBarImageView);
        LinkButton = findViewById(R.id.PageLinkButton);
        BackButtonImageView = findViewById(R.id.BackButton);

        ExpiryDateTextView.setVisibility(View.VISIBLE);
        BarcodeImageView.setVisibility(View.VISIBLE);
        BarcodeTextView.setVisibility(View.VISIBLE);
        BoxBarcodeImageView.setVisibility(View.VISIBLE);
        LinkButton.setVisibility(View.INVISIBLE);
        BackButtonImageView.setVisibility(View.INVISIBLE);
        PointsTextView.setVisibility(View.INVISIBLE);
        PointsLabelView.setVisibility(View.INVISIBLE);


        //Po zaludnieniu kontrolek, wygeneruj barcode
        generateBarcodeImage();


        BarcodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpiryDateTextView.setVisibility(View.INVISIBLE);
                BarcodeImageView.setVisibility(View.INVISIBLE);
                BarcodeTextView.setVisibility(View.INVISIBLE);
                BoxBarcodeImageView.setVisibility(View.INVISIBLE);
                LinkButton.setVisibility(View.VISIBLE);
                BackButtonImageView.setVisibility(View.VISIBLE);
                PointsTextView.setVisibility(View.VISIBLE);
                PointsLabelView.setVisibility(View.VISIBLE);
            }
        });

        LinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(CardLinkString);
            }
        });

        BackButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpiryDateTextView.setVisibility(View.VISIBLE);
                BarcodeImageView.setVisibility(View.VISIBLE);
                BarcodeTextView.setVisibility(View.VISIBLE);
                BoxBarcodeImageView.setVisibility(View.VISIBLE);
                LinkButton.setVisibility(View.INVISIBLE);
                BackButtonImageView.setVisibility(View.INVISIBLE);
                PointsTextView.setVisibility(View.INVISIBLE);
                PointsLabelView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
