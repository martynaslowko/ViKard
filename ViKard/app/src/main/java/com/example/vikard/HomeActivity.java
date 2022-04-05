package com.example.vikard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.Session.SessionManager;
import com.example.vikard.ui.login.LoginActivity;

import com.example.vikard.ui.login.LoginViewModel;
import com.example.vikard.ui.login.LoginViewModelFactory;
import com.example.vikard.ui.register.RegisterActivity;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private LoginRepository loginRepository;

    private Button signinButton;
    private Button signupButton;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLoggedIn())
        {
            String username = "";
            String passwsord = "";
            HashMap<String,String> a = sessionManager.getUserDetails();
            username = a.get("email");
            passwsord = a.get("password");

            loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                    .get(LoginViewModel.class);
            loginViewModel.login(username, passwsord);
            Intent intent = new Intent(this, MainScreen.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        signinButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.homeRegButton);

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