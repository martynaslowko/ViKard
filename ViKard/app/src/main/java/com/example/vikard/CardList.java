package com.example.vikard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.model.ShopModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CardList extends Fragment {

    ArrayList<CardModel> cardCollection = new ArrayList<CardModel>();
    View rootView;
    int user_id;

    public CardList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card_list, container, false);
        user_id = Integer.valueOf(LoginRepository.user.getUserId());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                populateCardCollection(user_id);
            }
        });
        t.start();
        t.setPriority(Thread.MAX_PRIORITY);
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bundle result = new Bundle();
        ArrayList<Integer> arr = new ArrayList<>();
        int size = cardCollection.size();
        for(int i = 0; i < size; i++) {
            ShopModel shopModel = new ShopModel(cardCollection.get(i).getShopsId(), false);
            arr.add(cardCollection.get(i).getShopsId());

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            CardListElement fm2 = new CardListElement(shopModel, cardCollection.get(i).getUsersCategory(), cardCollection.get(i).getId());
            fm.beginTransaction().add(R.id.card_list, fm2).commit();
            fragmentTransaction.commit();
        }
        result.putIntegerArrayList("123",arr);
        getParentFragmentManager().setFragmentResult("123", result);

        return rootView;
    }

    public void populateCardCollection (int UsersId){
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        try {
           Statement statement = conn.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT Id FROM Cards WHERE UsersId="+UsersId);
           while (resultSet.next()){
               cardCollection.add(new CardModel(resultSet.getInt("Id"), false));
           }
        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }


}