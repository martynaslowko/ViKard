package com.example.vikard.ui.card;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.vikard.MainScreen;
import com.example.vikard.R;
import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DeleteCardActivity extends AppCompatActivity {

    ArrayAdapter<String> dataAdapter;
    Spinner cardListSpinner;
    Button deleteCardButton;

    List<Integer> ids = new ArrayList<Integer>();
    int userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_card);



        //CardListSpinner
        userId = Integer.valueOf(LoginRepository.user.getUserId());
        deleteCardButton = findViewById(R.id.DeleteCardButton);
        cardListSpinner = findViewById(R.id.delCardListSpinner);


        if(!loadCardSpinnerData())
        {
            deleteCardButton.setEnabled(false);
        }
        else
        {
            deleteCardButton.setEnabled(true);
        }



        deleteCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int id = ids.get((int)cardListSpinner.getSelectedItemId());
                //Delete card function
                deleteCard(id);
                getBackToMain();
            }
        });




    }

    private List<String> getAllLabels()
    {
        List<String> labels = new ArrayList<String>();

        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        try {

            String sqlQuery = "SELECT s.Name, c.Id FROM Cards AS c JOIN Shops AS s ON c.ShopsId=s.Id WHERE UsersId = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(userId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                labels.add(resultSet.getString("Name"));
                ids.add(resultSet.getInt("Id"));

            }

        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
            return labels;
        }

    }


    private void deleteCard(int id){

        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        try
        {
            String sqlQuery = "DELETE FROM Cards WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
    }

    private boolean loadCardSpinnerData() {

        // Spinner Drop down elements
        List<String> labels = getAllLabels();
        if(labels.size() < 1)
        {
            return false;
        }
        else
        {
            // Creating adapter for spinner
            dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, labels);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            // attaching data adapter to spinner
            cardListSpinner.setAdapter(dataAdapter);
            return true;
        }

    }


    private void getBackToMain()
    {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        //getBackToMain();
        finish();
    }



}