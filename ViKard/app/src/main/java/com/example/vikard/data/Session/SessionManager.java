package com.example.vikard.data.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.vikard.ui.LoginRegister.HomeActivity;


import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int Private_mode = 0;

    private static final String PREF_NAME = "AndroidHivePref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_PASSWORD = "password";


    public SessionManager (Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        //pref = PreferenceManager.getDefaultSharedPreferences(context);

        editor = pref.edit();
    }

    public void createLoginSession(String email, String password){


        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_PASSWORD, password);

        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public HashMap<String , String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,null));

        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD,null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }
}