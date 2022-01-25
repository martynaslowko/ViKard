package com.example.vikard.ui.card;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vikard.CardList;
import com.example.vikard.R;
import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.model.CardModel;

import java.util.ArrayList;
import java.util.List;


public class EditCategoryActivity extends AppCompatActivity {


    Spinner cardListSpinner;
    ArrayAdapter<String> dataAdapter;
    int userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);


        //CardListSpinner
        userId = Integer.valueOf(LoginRepository.user.getUserId());

        cardListSpinner = findViewById(R.id.shopListSpinner);
        //loadCardSpinnerData();


    }
    private List<String> getAllLabels()
    {
//        List<String> labels = new ArrayList<String>();
//        SQLConnection sql = new SQLConnection();
//        Connection conn = sql.getConnection();
//        try {
//
//            String sqlQuery = "SELECT ShopsId FROM Cards WHERE UsersId = ?";
//            PreparedStatement statement = conn.prepareStatement(sqlQuery);
//            statement.setString(1, String.valueOf(userId));
//            ResultSet resultSet = statement.executeQuery("SELECT ShopsId FROM Cards WHERE UsersId = ?");
//            while (resultSet.next()){
//                labels.add(resultSet.getString("Name"));
//            }
//
//        } catch (Exception ex) {
//        } finally {
//            try { conn.close(); } catch (Exception e) { }
//            return labels;
//        }

        return null;

    }
    private void loadCardSpinnerData() {

        // Spinner Drop down elements
        List<String> labels = getAllLabels();

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        cardListSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}