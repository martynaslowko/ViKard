package com.example.vikard.data.Session;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.vikard.data.model.ShopModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShopSession {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;
    private static final String PREF_NAME = "ShopPref";

    private static final String IS_SAVED = "IsSaved";




    public ShopSession (Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        //pref = PreferenceManager.getDefaultSharedPreferences(context);

        editor = pref.edit();
    }

    public void createShopSession(){


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

    public void saveData(ArrayList<ShopModel> e) {

        Gson gson = new Gson();
        String json = gson.toJson(e);
        editor.putString("Shops", json);
        editor.apply();

    }


    public ArrayList<ShopModel> loadData() {
        Gson gson = new Gson();
        String json = pref.getString("Shops", null);
        Type type = new TypeToken<ArrayList<ShopModel>>() {}.getType();
        ArrayList<ShopModel> retval;
        retval = gson.fromJson(json, type);
        return retval;

    }




}