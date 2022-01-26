package com.example.vikard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.vikard.data.model.CardModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

        cardMiniature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardModel cardmodel = new CardModel(cardId_,true);
                Intent intent = new Intent(getActivity(), Karta.class);
                Bundle attrs = new Bundle();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = format.format(cardmodel.getExpiryDate());
                attrs.putString("shopName", shopName); //Nazwa sklepu
                attrs.putString("expireDate", strDate); //Data
                attrs.putString("barcode", cardmodel.getBarcode()); //Your id
                attrs.putString("hexColor", hexColor); //hex Color
                intent.putExtras(attrs);
                startActivity(intent);
            }
        });
        return rootView;
    }
}