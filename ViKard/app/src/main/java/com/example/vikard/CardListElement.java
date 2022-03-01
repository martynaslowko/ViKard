package com.example.vikard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikard.data.model.CardModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CardListElement extends Fragment {
    View rootView;
    ImageView cardMiniature;
    TextView shopName_;
    TextView shopCategory_;
    String shopName;
    String shopCategory;
    String hexColor;
    int cardId_;

    public CardListElement(String shopname, String shopcategory, String hexcolor, int id) {
        shopName = shopname;
        shopCategory = shopcategory;
        hexColor = hexcolor;
        cardId_ = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card_list_element, container, false);
        cardMiniature = (ImageView) rootView.findViewById(R.id.cardImage);
        shopName_ = (TextView) rootView.findViewById(R.id.shopName);
        shopCategory_ = (TextView) rootView.findViewById(R.id.shopCategory);

        cardMiniature.setColorFilter(Color.parseColor("#"+hexColor));
        shopName_.setText(shopName);
        shopCategory_.setText(shopCategory);

        //listener, że jak naciśnie się na kartę, to otwiera się okienko z podglądem karty... nie działa
        cardMiniature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "MM/dd/yy";
                DateFormat df = new SimpleDateFormat(pattern);
                CardModel cardmodel = new CardModel(cardId_,true);
                Date ed = cardmodel.getExpiryDate();
                String expire_date;
                if(ed != null){
                    expire_date = df.format(ed);
                }
                else{
                    expire_date = "";
                }

                Intent intent = new Intent(getActivity(), Karta.class);
                Bundle attrs = new Bundle();
                attrs.putString("shopName", shopName); //Nazwa sklepu
                attrs.putString("expireDate", expire_date); //Data
                attrs.putString("barcode", new CardModel(cardId_,true).getBarcode()); //Your id
                intent.putExtras(attrs);
                startActivity(intent);
            }
        });
        return rootView;
    }
}