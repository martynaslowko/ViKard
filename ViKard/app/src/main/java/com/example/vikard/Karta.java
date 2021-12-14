package com.example.vikard;
import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.SQLConnection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
public class Karta extends Fragment {
    String shopName_;
    java.util.Date expireDate_;
    String barcode_;
    View rootView;
    TextView shopName__;
    TextView expireDate__;
    TextView barcode__;

    Karta(String shopName, java.util.Date expireDate, String barcode){
        shopName_ = shopName;
        expireDate_ = expireDate;
        barcode_ = barcode;
    }
    protected View onCreate(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.karta, container, false);
        shopName__ = (TextView) rootView.findViewById(R.id.shopName);
        expireDate__ = (TextView) rootView.findViewById(R.id.cardExpires);
        barcode__ = (TextView) rootView.findViewById(R.id.barcode);

        shopName__.setText(shopName_);
        //expireDate__.setText(expireDate_);
        barcode__.setText(barcode_);
        return rootView;
    }
}
