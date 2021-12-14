package com.example.vikard.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import com.example.vikard.HomeActivity;
import com.example.vikard.MainScreen;
import com.example.vikard.R;
import com.example.vikard.databinding.ActivityRegisterBinding;

import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {


    private RegisterViewModel RegisterViewModel;
    private ActivityRegisterBinding binding;
    private String formattedDate = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Populowanie bindingu, żeby móć się odwoływać do wszystkich kontrolek w danym widoku
        //Zastępuje findViewById()
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);


        // Konstruktory, bindingi itp

        final DatePickerDialog[] picker = new DatePickerDialog[1];
        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;

        final EditText birthdayEditText = binding.birthDate;
        birthdayEditText.setInputType(InputType.TYPE_NULL);
        birthdayEditText.setFocusable(false);


        final EditText emailEditText = binding.email;
        final EditText r_passwordEditText = binding.rPassword;
        final EditText r_repasswordEditText = binding.rRepassword;

        final Button registerButton = binding.regButton;
        registerButton.setEnabled(false);

        //



        //Logika przycisku register, co dzieje się po kliknięciu przycisku register
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                RegisterViewModel.register(firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        formattedDate,
                        r_repasswordEditText.getText().toString());

                updateUiWithUser();

            }


        });

        //Obserwator sprawdza czy isDataValid zwrócił true;
        //Jeśli tak to włączy przycisk register i można przejść do rejestracji

        RegisterViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if(registerFormState == null)
                {
                    return;
                }

                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));

                }
                if (registerFormState.getPasswordError() != null) {
                    r_passwordEditText.setError(getString(registerFormState.getPasswordError()));

                }
                if(registerFormState.getFirstNameError() != null)
                {
                    firstNameEditText.setError(getString(registerFormState.getFirstNameError()));

                }
                if(registerFormState.getLastNameError() != null)
                {
                    lastNameEditText.setError(getString(registerFormState.getLastNameError()));

                }
                if(registerFormState.getBirthdateError() != null)
                {
                    //birthdayEditText.setError(getString(registerFormState.getBirthdateError()));

                }
                if(registerFormState.getRepasswordError() != null)
                {
                    r_repasswordEditText.setError(getString(registerFormState.getRepasswordError()));

                }


            }
        });

        //Text listener mega nieoptymalne mam wrażenie ale nie wiem jak inaczej to zrobic
        //Przy każdym wpisanym znaku w rejestracji wywołuje się afterTextChanged
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

        birthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                //picker dialog
                if(picker[0] == null)
                {
                    picker[0] = new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    birthdayEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    formattedDate = "" + year+(monthOfYear+1)+dayOfMonth;
                                }
                            }, year, month, day);
                    picker[0].show();
                }
                else
                {
                    picker[0].show();
                }


            }

        });


        emailEditText.addTextChangedListener(afterTextChangedListener1);
        r_passwordEditText.addTextChangedListener(afterTextChangedListener1);
        firstNameEditText.addTextChangedListener(afterTextChangedListener1);
        lastNameEditText.addTextChangedListener(afterTextChangedListener1);
        birthdayEditText.addTextChangedListener(afterTextChangedListener1);
        r_repasswordEditText.addTextChangedListener(afterTextChangedListener1);


    }
    //Finish ze względu, że po udanej rejestracji nie ma potrzeby bycia w tym widoku
    //Wraca do poprzedniego Activity w tym przypadku HomeScreen
    private void updateUiWithUser() {
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main_screen);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }



}