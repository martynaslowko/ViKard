package com.example.vikard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import androidx.viewpager2.widget.ViewPager2;

import com.example.vikard.data.Session.SessionManager;
import com.example.vikard.ui.card.AddCardActivity;
import com.example.vikard.ui.card.DeleteCardActivity;
import com.example.vikard.ui.card.EditCategoryActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {
    TabLayout tablayout;
    ViewPager2 viewpager2;
    FragmentAdapter fragmentadapter;

    SessionManager sessionManager;
    private boolean isClicked = false;
    FloatingActionButton dropFloatButton;
    FloatingActionButton addFloatButton;
    FloatingActionButton delFloatButton;
    FloatingActionButton editFloatButton;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;

    Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main_screen);

        //Session
        sessionManager = new SessionManager(getApplicationContext());

        tablayout = findViewById(R.id.tablayout);
        viewpager2 = findViewById(R.id.view_pager);
        FragmentManager fm = getSupportFragmentManager();
        fragmentadapter = new FragmentAdapter(fm, getLifecycle());
        viewpager2.setAdapter(fragmentadapter);

        //Tutaj wyłączyłem "Swipe" z lewej do prawej i z prawej do lewej w viewpagerze
        //W przyszłośći będzie można to jakoś skonfigurować i przywrócić swipe
        //https://www.droidcon.com/2021/10/06/controlling-swipe-direction-with-viewpager2/
        viewpager2.setUserInputEnabled(false);


        tablayout.addTab(tablayout.newTab().setText("Karty"));
        tablayout.addTab(tablayout.newTab().setText("Mapa"));


        dropFloatButton = (FloatingActionButton)findViewById(R.id.dropDown);
        addFloatButton = (FloatingActionButton)findViewById(R.id.addCardButton);
        delFloatButton = (FloatingActionButton)findViewById(R.id.deleteCardButton);
        editFloatButton = (FloatingActionButton)findViewById(R.id.editCardButton);

        fabOpen = AnimationUtils.loadAnimation
                (this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation
                (this,R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation
                (this,R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation
                (this,R.anim.rotate_backward);


        logoutButton = (Button)findViewById(R.id.LogoutButton);
        logoutButton.setVisibility(View.VISIBLE);

        dropFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(isClicked);
                if(!isClicked)
                {
                    isClicked=true;
                    animateFab();
                }

                else
                {
                    isClicked=false;
                    animateFab();
                }


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
        delFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteCategoryActivity();
            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();
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

    public void openDeleteCategoryActivity()
    {
        Intent intent = new Intent(this, DeleteCardActivity.class);
        startActivity(intent);
    }


    private void animateFab(){
        if (!isClicked){
            dropFloatButton.startAnimation(rotateForward);
            addFloatButton.startAnimation(fabClose);
            editFloatButton.startAnimation(fabClose);
            delFloatButton.startAnimation(fabClose);
            addFloatButton.setClickable(false);
            editFloatButton.setClickable(false);
            delFloatButton.setClickable(false);
            isClicked=false;
        }else {
            dropFloatButton.startAnimation(rotateBackward);
            addFloatButton.startAnimation(fabOpen);
            editFloatButton.startAnimation(fabOpen);
            delFloatButton.startAnimation(fabOpen);
            addFloatButton.setClickable(true);
            editFloatButton.setClickable(true);
            delFloatButton.setClickable(true);
            isClicked=true;
        }
    }



    //Zrzuca apke do pulpitu.
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}