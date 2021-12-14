package com.example.vikard;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikard.data.LoginRepository;

import java.util.List;

public class CardListElement extends Fragment {
    View rootView;
    ImageView cardMiniature;
    TextView shopName_;
    TextView shopCategory_;
    String shopName;
    String shopCategory;
    String hexColor;

    public CardListElement(String shopname, String shopcategory, String hexcolor) {
        shopName = shopname;
        shopCategory = shopcategory;
        hexColor = hexcolor;
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
                View karta = new Karta();
            }
        });
        return rootView;
    }
}