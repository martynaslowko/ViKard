package com.example.vikard.ui.register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vikard.R;
import com.example.vikard.ui.register.registerFormState;

public class registerPatternCheck extends ViewModel {


    private MutableLiveData<registerFormState> registerFormState = new MutableLiveData<>();

    LiveData<registerFormState> getLoginFormState() {
        return registerFormState;
    }

    public void registerDataChanged(String username, String password) {
        if (!isEmailAdressValid(username)) {
            registerFormState.setValue(new registerFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new registerFormState(null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new registerFormState(true));
        }
    }


    // A placeholder username validation check
    private boolean isEmailAdressValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
