package com.example.vikard.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.vikard.databinding.ActivityRegisterBinding;


public class activity_register extends AppCompatActivity {


    private RegisterViewModel RegisterViewModel;
    private ActivityRegisterBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        //registerPatternCheck = new ViewModelProvider(this, new LoginViewModelFactory()).get(registerPatternCheck.class);
        setContentView(binding.getRoot());
        RegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);




        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;
        final EditText birthdayEditText = binding.birthDate;
        final EditText emailEditText = binding.email;
        final EditText r_passwordEditText = binding.rPassword;
        final EditText r_repasswordEditText = binding.rRepassword;

        final Button registerButton = binding.regButton;
        registerButton.setEnabled(false);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //Jestem idiotą więc dodaje to w ten sposób.
                //Jak macie problem to spoko ale ja nie pytałem.
                //Dodawanie do bazy danych
                //Nie działa i chuj
                RegisterViewModel.register(emailEditText.getText().toString(),
                        r_repasswordEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        birthdayEditText.getText().toString());

            }


        });

        //Obserwator sprawdza czy isDataValid zwrócił true;
        //Jeśli tak to włączy przycisk register i można przejść do rejestracji


        RegisterViewModel.getRegisterFormState().observe(this, new Observer<registerFormState>() {
            @Override
            public void onChanged(@Nullable registerFormState registerFormState) {
                if(registerFormState == null)
                {
                    return;
                }

                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                    registerButton.setEnabled(false);
                }
                else if (registerFormState.getPasswordError() != null) {
                    r_passwordEditText.setError(getString(registerFormState.getPasswordError()));
                    registerButton.setEnabled(false);
                }
                else if(registerFormState.getFirstNameError() != null)
                {
                    firstNameEditText.setError(getString(registerFormState.getFirstNameError()));
                    registerButton.setEnabled(false);
                }
                else if(registerFormState.getLastNameError() != null)
                {
                    lastNameEditText.setError(getString(registerFormState.getLastNameError()));
                    registerButton.setEnabled(false);
                }
                else if(registerFormState.getBirthdateError() != null)
                {
                    lastNameEditText.setError(getString(registerFormState.getBirthdateError()));
                    registerButton.setEnabled(false);
                }
                else if(registerFormState.getRepasswordError() != null)
                {
                    r_repasswordEditText.setError(getString(registerFormState.getRepasswordError()));
                    registerButton.setEnabled(false);
                }


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
                RegisterViewModel.registerDataChanged(emailEditText.getText().toString(), r_passwordEditText.getText().toString(),
                firstNameEditText.getText().toString(),lastNameEditText.getText().toString(),birthdayEditText.getText().toString(),
                        r_repasswordEditText.getText().toString());


            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener1);
        r_passwordEditText.addTextChangedListener(afterTextChangedListener1);
        firstNameEditText.addTextChangedListener(afterTextChangedListener1);
        lastNameEditText.addTextChangedListener(afterTextChangedListener1);
        birthdayEditText.addTextChangedListener(afterTextChangedListener1);
        r_repasswordEditText.addTextChangedListener(afterTextChangedListener1);


    }




}