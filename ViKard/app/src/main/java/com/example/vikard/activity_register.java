package com.example.vikard;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.vikard.databinding.ActivityRegisterBinding;
import com.example.vikard.ui.register.registerPatternCheck;

public class activity_register extends AppCompatActivity {


    private registerPatternCheck registerPatternCheck;
    private ActivityRegisterBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        //registerPatternCheck = new ViewModelProvider(this, new LoginViewModelFactory()).get(registerPatternCheck.class);
        setContentView(binding.getRoot());



        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;
        final EditText birthdayEditText = binding.birthDate;
        final EditText emailEditText = binding.email;
        final EditText reemailEditText = binding.reEmail;
        final EditText r_passwordEditText = binding.rPassword;
        final EditText r_repasswordEditText = binding.rRepassword;

        final Button registerButton = findViewById(R.id.regButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }


        });


        TextWatcher afterTextChangedListener1 = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerPatternCheck.registerDataChanged(emailEditText.getText().toString(),
                        r_passwordEditText.getText().toString());


            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener1);
        r_passwordEditText.addTextChangedListener(afterTextChangedListener1);


    }




}