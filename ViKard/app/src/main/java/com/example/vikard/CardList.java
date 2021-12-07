package com.example.vikard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.model.CardModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CardList extends Fragment {

    ArrayList<CardModel> cardCollection = new ArrayList<CardModel>();

    public CardList() {
        //populateCardCollection(Integer.valueOf(LoginRepository.user.getUserId()));
        //populates cardCollection object with current logged-in user
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_list, container, false);
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