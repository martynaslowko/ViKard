package com.example.vikard.ui.card;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.vikard.MainScreen;
import com.example.vikard.R;
import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.Session.CardSession;
import com.example.vikard.data.Session.ShopSession;
import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.model.ShopModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class EditCategoryActivity extends AppCompatActivity {

    ArrayAdapter<String> dataAdapter;
    Spinner cardListSpinner;
    Button changeCategoryButton;
    EditText categoryEditTextBox;
    List<Integer> ids = new ArrayList<Integer>();
    int userId;
    private ArrayList<CardModel> tempc= new ArrayList<>();
    private ArrayList<ShopModel> temp= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);


        categoryEditTextBox = findViewById(R.id.editTextCategory);

        //CardListSpinner
        userId = Integer.valueOf(LoginRepository.user.getUserId());

        cardListSpinner = findViewById(R.id.cardListSpinner);
        loadCardSpinnerData();

        CardSession cs = new CardSession(getApplicationContext());
        ShopSession ss = new ShopSession(getApplicationContext());
        changeCategoryButton = findViewById(R.id.EditCategoryCardButton);
        changeCategoryButton.setEnabled(false);
        changeCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int id = ids.get((int)cardListSpinner.getSelectedItemId());
                String userCategory = categoryEditTextBox.getText().toString();
                CardModel a = new CardModel(id,false);
                a.changeUsersCategory(userCategory);
                //getBackToMain();
                tempc= cs.loadData();
                temp = ss.loadData();
                temp.set((int)cardListSpinner.getSelectedItemId(),a.getShop());
                tempc.set((int)cardListSpinner.getSelectedItemId(),a);
                ss.clearData();
                cs.clearData();
                ss.createShopSession();
                cs.createCardSession();
                ss.saveData(temp);
                cs.saveData(tempc);



                finish();
            }
        });

        TextWatcher scannedTextChange = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ignore
                if(s.toString().trim().length()==0){
                    changeCategoryButton.setEnabled(false);
                } else {
                    changeCategoryButton.setEnabled(true);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        };
        categoryEditTextBox.addTextChangedListener(scannedTextChange);

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