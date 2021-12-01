package com.example.vikard.ui.register;

import com.example.vikard.ui.register.RegisterDataSource;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vikard.R;
import com.example.vikard.ui.register.registerFormState;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {


    private RegisterDataSource rds;

    private MutableLiveData<registerFormState> registerFormState = new MutableLiveData<>();

    public LiveData<registerFormState> getRegisterFormState() {
        return registerFormState;
    }


    public void register(String username, String password, String firstName,
                         String lastName, String birthDate )
    {
         rds.executeRegisterQuery(username,password,firstName,lastName,birthDate);

    }


    //Czerwone komunikaty wyświetlające się przy błędnym wpisaniu w polu.
    //R.string to odnośnik do pliku strings.xml w którym można je modyfikować.

    public void registerDataChanged(String username, String password, String firstName,
                                    String lastName, String birthDate, String repassword)
    {
        if (!isEmailAdressValid(username))
        {
            registerFormState.setValue(new registerFormState(R.string.invalid_email, null,null,null,null,null));
        }
        if (!isPasswordValid(password))
        {
            registerFormState.setValue(new registerFormState(null, R.string.invalid_password,null,null,null,null));
        }
        if(!isFirstNameValid(firstName))
        {
            registerFormState.setValue(new registerFormState(null,null,R.string.invalidFirstName, null,null,null));
        }
        if(!isValidLastName(lastName))
        {
            registerFormState.setValue(new registerFormState(null,null,null, R.string.invalidLastName,null,null));
        }
        if(!isValidBirthDay(birthDate))
        {
            registerFormState.setValue(new registerFormState(null,null,null, null,R.string.invalidBirthday,null));
        }
        if(!arePasswordsEqual(password, repassword))
        {
            registerFormState.setValue(new registerFormState(null,null,null,null,null,R.string.invalid_repassword));
        }
        else {
            registerFormState.setValue(new registerFormState(true));
        }
    }


    // A placeholder username validation check
    private boolean isEmailAdressValid(String email)
    {
        if (email == null) {
            return false;
        }
        if (email.contains("@"))
        {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        if(!email.contains("@"))
        {
            return false;
        }
        else {
            return !email.trim().isEmpty();
        }
    }

    //Metody sprawdzające poprawność pól.

    private boolean isFirstNameValid(String firstName)
    {
        return firstName.matches( "[A-Z][a-z]*" );

    }

    private boolean isValidLastName(String lastName)
    {
        return lastName.matches( "[A-Z][a-z]*" );
    }

    private boolean isValidBirthDay(String birthDate)
    {
        //Todo logika na wykrywanie dobrej daty

        return true;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password)
    {
        return password != null && password.trim().length() > 5;
    }

    private boolean arePasswordsEqual(String password, String repassword)
    {
        if(password.equals(repassword))
        {
            return true;
        }
        return false;
    }

    /////
}
