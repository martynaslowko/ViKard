package com.example.vikard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.model.ShopModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CardListElement extends Fragment {
    View rootView;
    ImageView cardMiniature;
    TextView shopName_;
    TextView shopCategory_;
    ShopModel shop;
    String shopCategory;
    ImageView button;
    int cardId_;

    public CardListElement(ShopModel shopModel, String shopcategory, int id) {
        shop = shopModel;
        shopCategory = shopcategory;
        cardId_ = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card_list_element, container, false);
        cardMiniature = (ImageView) rootView.findViewById(R.id.cardImage);
        shopName_ = (TextView) rootView.findViewById(R.id.shopName);
        shopCategory_ = (TextView) rootView.findViewById(R.id.shopCategory);
        button = (ImageView) rootView.findViewById(R.id.cardButton);

        cardMiniature.setColorFilter(Color.parseColor("#"+shop.getHexColor()));
        shopName_.setText(shop.getName());
        shopCategory_.setText(shopCategory);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCard();
            }
        });
        return rootView;
    }
    void viewCard(){
        CardModel cardmodel = new CardModel(cardId_,true);
        shop.setAll();
        Intent intent = new Intent(getActivity(), Karta.class);
        Bundle attrs = new Bundle();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = format.format(cardmodel.getExpiryDate());
        attrs.putString("shopName", shop.getName()); //Nazwa sklepu
        attrs.putString("expireDate", strDate); //Data
        attrs.putString("barcode", cardmodel.getBarcode()); //Your id
        attrs.putString("hexColor", shop.getHexColor()); //hex Color
        attrs.putString("link", shop.getHomeLink()); //storeLink
        intent.putExtras(attrs);
        startActivity(intent);
    }
}