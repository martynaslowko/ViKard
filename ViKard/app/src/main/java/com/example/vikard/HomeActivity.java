package com.example.vikard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vikard.ui.login.LoginActivity;

import com.example.vikard.ui.login.ShopLoginActivity;
import com.example.vikard.ui.register.RegisterActivity;

public class HomeActivity extends AppCompatActivity {

    private Button signinButton;
    private Button signupButton;
    private Button signinShopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        signinButton = (Button) findViewById(R.id.LoginButton);
        signupButton = (Button) findViewById(R.id.homeRegButton);
        signinShopButton = (Button) findViewById(R.id.ShopLoginButton);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginScreen();
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterScreen();
            }
        });

        signinShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShopLoginScreen();
            }
        });

    }

    private void openShopLoginScreen() {
        Intent intent = new Intent(this, ShopLoginActivity.class);
        startActivity(intent);
    }


    public void openLoginScreen()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }


    public void openRegisterScreen()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }



}