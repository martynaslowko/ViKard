package com.example.vikard.ui.register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vikard.R;

public class RegisterViewModel extends ViewModel {


    private RegisterDataSource rds;

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();

    public LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }


    //Metoda rejestracji wykonująca się po wciśnięciu przycisku registerButton
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
            registerFormState.setValue(new RegisterFormState(R.string.invalid_email, null,null,null,null,null));
        }
        if (!isPasswordValid(password))
        {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password,null,null,null,null));
        }
        if(!isFirstNameValid(firstName))
        {
            registerFormState.setValue(new RegisterFormState(null,null,R.string.invalidFirstName, null,null,null));
        }
        if(!isValidLastName(lastName))
        {
            registerFormState.setValue(new RegisterFormState(null,null,null, R.string.invalidLastName,null,null));
        }
        if(!isValidBirthDay(birthDate))
        {
            registerFormState.setValue(new RegisterFormState(null,null,null, null,R.string.invalidBirthday,null));
        }
        if(!arePasswordsEqual(password, repassword))
        {
            registerFormState.setValue(new RegisterFormState(null,null,null,null,null,R.string.invalid_repassword));
        }
        else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    //Metody sprawdzające poprawność pól.

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
            return true;
        else if(repassword.equals(password))
            return true;

        return false;
    }

    /////
}
