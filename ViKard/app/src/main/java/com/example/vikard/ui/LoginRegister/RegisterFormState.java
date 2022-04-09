package com.example.vikard.ui.LoginRegister;

import androidx.annotation.Nullable;

public class RegisterFormState {

    @Nullable
    private Integer emailError;

    @Nullable
    private Integer repasswordError;
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer birthdateError;


    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer firstNameError, @Nullable Integer lastNameError, @Nullable Integer birthdateError, @Nullable Integer repasswordError) {
        this.emailError = usernameError;
        this.passwordError = passwordError;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.birthdateError = birthdateError;
        this.repasswordError = repasswordError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.firstNameError = null;
        this.lastNameError = null;
        this.birthdateError = null;
        this.repasswordError = null;
        this.isDataValid = isDataValid;
    }


    //Gettery
    @Nullable
    Integer getEmailError() {
        return emailError;
    }
    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    Integer getFirstNameError() {
        return firstNameError;
    }
    @Nullable
    Integer getLastNameError() {
        return lastNameError;
    }
    @Nullable
    Integer getBirthdateError() { return birthdateError; }
    @Nullable
    Integer getRepasswordError() { return repasswordError; }


    boolean isDataValid() {
        return isDataValid;
    }

}
