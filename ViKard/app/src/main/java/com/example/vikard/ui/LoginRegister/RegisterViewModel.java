package com.example.vikard.ui.LoginRegister;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vikard.R;

public class RegisterViewModel extends ViewModel {


    private RegisterDataSource rds = new RegisterDataSource();

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
        else if (!isPasswordValid(password))
        {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password,null,null,null,null));
        }
        else if(!arePasswordsEqual(password, repassword))
        {
            registerFormState.setValue(new RegisterFormState(null,null,null,null,null,R.string.invalid_repassword));
        }

        else if(!isFirstNameValid(firstName))
        {
            registerFormState.setValue(new RegisterFormState(null,null,R.string.invalidFirstName, null,null,null));
        }
        else if(!isValidLastName(lastName))
        {
            registerFormState.setValue(new RegisterFormState(null,null,null, R.string.invalidLastName,null,null));
        }
        else if(!isValidBirthDay(birthDate))
        {
            registerFormState.setValue(new RegisterFormState(null,null,null, null,R.string.invalidBirthday,null));
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
        if((firstName != null) && firstName.matches( "[A-Z][a-z]*" ))
            return true;
        else
            return false;
    }

    private boolean isValidLastName(String lastName)
    {
        if((lastName != null) && lastName.matches( "[A-Z][a-z]*" ))
            return true;
        else
            return false;
    }

    private boolean isValidBirthDay(String birthDate)
    {
        //Zwraca true gdy = dd/mm/yyyy, dd-mm-yyyy or dd.mm.yyyy
        if ((birthDate != null) && birthDate.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"))
            return true;
        else
            return false;
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
