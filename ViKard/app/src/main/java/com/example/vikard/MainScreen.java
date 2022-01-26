package com.example.vikard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.viewpager2.widget.ViewPager2;

import com.example.vikard.data.model.CardModel;
import com.example.vikard.ui.card.AddCardActivity;
import com.example.vikard.ui.card.EditCategoryActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {
    TabLayout tablayout;
    ViewPager2 viewpager2;
    FragmentAdapter fragmentadapter;


    private boolean isClicked = false;
    FloatingActionButton dropFloatButton;
    FloatingActionButton addFloatButton;
    FloatingActionButton delFloatButton;
    FloatingActionButton editFloatButton;
    ArrayList<CardModel> ckd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        tablayout = findViewById(R.id.tablayout);
        viewpager2 = findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        fragmentadapter = new FragmentAdapter(fm, getLifecycle());
        viewpager2.setAdapter(fragmentadapter);

//        CardList frag = (CardList)fm.findFragmentById(R.id.FirstFragment);
//        if(frag != null && frag.isAdded())
//        {
//            ckd = frag.cardCollection;
//        }

        tablayout.addTab(tablayout.newTab().setText("Karty"));
        tablayout.addTab(tablayout.newTab().setText("Mapa"));


        dropFloatButton = (FloatingActionButton)findViewById(R.id.dropDown);
        addFloatButton = (FloatingActionButton)findViewById(R.id.addCardButton);
        delFloatButton = (FloatingActionButton)findViewById(R.id.deleteCardButton);
        editFloatButton = (FloatingActionButton)findViewById(R.id.editCardButton);


        dropFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(isClicked);
                if(!isClicked)
                    isClicked=true;
                else
                    isClicked=false;

            }
        });

        addFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddCardActivity();
            }
        });

        editFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditCategoryActivity();
            }
        });


        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tablayout.selectTab(tablayout.getTabAt(position));
            }
        });
    }

    private void setVisibility(boolean clicked)
    {
        if(!clicked)
        {
           addFloatButton.setVisibility(View.VISIBLE);
           delFloatButton.setVisibility(View.VISIBLE);
           editFloatButton.setVisibility(View.VISIBLE);
        }
        else
        {
            addFloatButton.setVisibility(View.INVISIBLE);
            delFloatButton.setVisibility(View.INVISIBLE);
            editFloatButton.setVisibility(View.INVISIBLE);
        }
    }
    public void openAddCardActivity()
    {
        Intent intent = new Intent(this, AddCardActivity.class);

        startActivity(intent);
    }

    public void openEditCategoryActivity()
    {

        Intent intent = new Intent(this, EditCategoryActivity.class);
        startActivity(intent);
    }






    //Zrzuca apke do pulpitu.
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}