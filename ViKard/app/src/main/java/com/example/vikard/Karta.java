package com.example.vikard;
import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.SQLConnection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
public class Karta extends AppCompatActivity {
    String shopName;
    String expireDate;
    String barcode;
    TextView shopName_;
    TextView expireDate_;
    TextView barcode_;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.karta);

        shopName_ = (TextView) findViewById(R.id.shopName);
        expireDate_ = (TextView) findViewById(R.id.cardExpires);
        barcode_ = (TextView) findViewById(R.id.barcode);

        Bundle b = getIntent().getExtras();
        if(b != null){
            shopName = b.getString("shopName");
            expireDate = b.getString("expireDate");
            barcode = b.getString("barcode");

            shopName_.setText(shopName);
            expireDate_.setText(expireDate);
            barcode_.setText(barcode);
        }

    }
}
