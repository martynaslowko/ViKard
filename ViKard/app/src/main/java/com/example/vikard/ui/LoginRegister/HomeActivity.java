package com.example.vikard.ui.LoginRegister;


import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.example.vikard.MainScreen;
import com.example.vikard.R;
import com.example.vikard.data.Session.SessionManager;
import com.example.vikard.databinding.ActivityHomeBinding;

import java.util.Calendar;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private LoginViewModel loginViewModelShop;
    private RegisterViewModel RegisterViewModel;
    private String formattedDate = "";
    private ViewFlipper viewFlipper;
    private ActivityHomeBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Sessionmanager check
        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.isLoggedIn()) {
            String username = "";
            String passwsord = "";
            HashMap<String, String> a = sessionManager.getUserDetails();
            username = a.get("email");
            passwsord = a.get("password");

            loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
            loginViewModelShop = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
            loginViewModel.login(username, passwsord,false);
            loginViewModelShop.login(username, passwsord,true);
            Intent intent = new Intent(this, MainScreen.class);
            startActivity(intent);
        }

        //Inflate vars and viewflipper
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewFlipper = findViewById(R.id.view_flipper); //ViewFlipper ids -> 0-home,1-login,2-register etc.

        //LoginLayout vars
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        //ShopLoginLayout vars
        final EditText usernameEditText2 = binding.username2;
        final EditText passwordEditText2 = binding.password2;
        final Button loginButton2 = binding.login2;
        final ProgressBar loadingProgressBar2 = binding.loading2;

        //RegisterLayout vars
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

        ////////////////

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginViewModelShop = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        RegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);


        //HomeScreenLayout space
        binding.LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewFlipper.showNext();
                viewFlipper.setInAnimation(getApplicationContext(),R.anim.slide_in_right);
                viewFlipper.setOutAnimation(getApplicationContext(),R.anim.slide_out_left);
                viewFlipper.setDisplayedChild(1);
            }
        });

        binding.ShopLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewFlipper.showNext();
                viewFlipper.setInAnimation(getApplicationContext(),R.anim.slide_in_right);
                viewFlipper.setOutAnimation(getApplicationContext(),R.anim.slide_out_left);
                viewFlipper.setDisplayedChild(3);
            }
        });

        binding.homeRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(getApplicationContext(),R.anim.slide_in_right);
                viewFlipper.setOutAnimation(getApplicationContext(),R.anim.slide_out_left);
                viewFlipper.setDisplayedChild(2);
            }
        });

        //LoginScreenLayout space
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModelShop.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton2.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText2.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText2.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    //Return ze względu na to że nie chce zeby po błędnym zalogowaniu wracał do homescreenu.
                    return;
                }
                if (loginResult.getSuccess() != null) {
                    sessionManager.createLoginSession(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        loginViewModelShop.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar2.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    //Return ze względu na to że nie chce zeby po błędnym zalogowaniu wracał do homescreenu.
                    return;
                }
                if (loginResult.getSuccess() != null) {
                    sessionManager.createLoginSession(usernameEditText2.getText().toString(), passwordEditText2.getText().toString());
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        TextWatcher afterTextChangedListenerShop = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModelShop.loginDataChanged(usernameEditText2.getText().toString(),
                        passwordEditText2.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        usernameEditText2.addTextChangedListener(afterTextChangedListenerShop);
        passwordEditText2.addTextChangedListener(afterTextChangedListenerShop);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), false);
                }
                return false;
            }
        });



        passwordEditText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText2.getText().toString(),
                            passwordEditText2.getText().toString(), false);
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), false);
            }
        });

        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText2.getText().toString(),
                        passwordEditText2.getText().toString(), true);
            }
        });

        //RegisterScreenLayout space
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                RegisterViewModel.register(firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        formattedDate,
                        r_repasswordEditText.getText().toString());

                viewFlipper.showPrevious();
                viewFlipper.setInAnimation(getApplicationContext(),android.R.anim.slide_in_left);
                viewFlipper.setOutAnimation(getApplicationContext(),android.R.anim.slide_out_right);
            }
        });

        RegisterViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if(registerFormState == null){
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
                if(registerFormState.getRepasswordError() != null) {
                    r_repasswordEditText.setError(getString(registerFormState.getRepasswordError()));
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
                    picker[0] = new DatePickerDialog(HomeActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    birthdayEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    formattedDate = "" + year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
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




        ////////////////////////////////////////////////////








    }


    //Functions

    private void updateUiWithUser(LoggedInUserView model) {
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main_screen);
        switchActivities();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();

    }

    //Po udanym logowaniu przechodzi do MainScreen.class
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainScreen.class);
        startActivity(switchActivityIntent);
    }



    @Override
    public void onBackPressed() {
        viewFlipper.setInAnimation(getApplicationContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getApplicationContext(),android.R.anim.slide_out_right);
        viewFlipper.setDisplayedChild(0);
    }
}