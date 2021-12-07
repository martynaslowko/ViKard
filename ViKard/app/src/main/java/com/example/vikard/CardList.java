package com.example.vikard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.model.LoggedInUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class CardList extends Fragment {

    ArrayList<CardModel> cardCollection = new ArrayList<CardModel>();
    TextView tv1;
    View rootView;
    int user_id;

    public CardList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card_list, container, false);
        user_id = Integer.valueOf(LoginRepository.user.getUserId());
        //populateCardCollection(Integer.valueOf(LoginRepository.user.getUserId()));
        //populates cardCollection object with current logged-in user
        populateCardCollection(user_id);

        tv1 = (TextView) rootView.findViewById(R.id.output);
        tv1.setText(String.valueOf(user_id));

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