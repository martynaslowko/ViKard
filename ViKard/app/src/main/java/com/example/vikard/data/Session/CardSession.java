package com.example.vikard.data.Session;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.vikard.data.model.CardModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CardSession {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    private static final String PREF_NAME = "CardPref";

    private static final String IS_SAVED = "IsSaved";
    ArrayList<CardModel> retval = new ArrayList<>();



    public CardSession (Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createCardSession(){
        editor.putBoolean(IS_SAVED,true);
        editor.commit();
    }

    public boolean isSaved(){
        return pref.getBoolean(IS_SAVED, false);
    }



public void clearData()
{
    editor.clear();
    editor.commit();
}

    public void saveData(ArrayList<CardModel> e) {

        Gson gson = new Gson();
        String json = gson.toJson(e);
        editor.putString("Cards", json);
        editor.apply();

    }


    public ArrayList<CardModel> loadData() {

        Gson gson = new Gson();
        String json = pref.getString("Cards", null);
        Type type = new TypeToken<ArrayList<CardModel>>() {}.getType();
        retval = gson.fromJson(json, type);
        return retval;

    }




}