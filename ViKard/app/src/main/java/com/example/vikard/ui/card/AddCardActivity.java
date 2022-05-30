package com.example.vikard.ui.card;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vikard.MainScreen;
import com.example.vikard.R;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.model.CardModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddCardActivity extends AppCompatActivity {

    public ArrayAdapter<String> dataAdapter;

    Button btnBarcode;
    Button btnaddCardToDB;
    TextView scannedText;

    Spinner shplistSpinner;
    final DatePickerDialog[] picker = new DatePickerDialog[1];
    private String formattedDate = "";
    EditText expiryDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        //ShoplistSpinner
        shplistSpinner = findViewById(R.id.shopListSpinner);
        loadSpinnerData();

        //Scanner button
        btnBarcode = findViewById(R.id.initiateScan);


        //Text scannedResult
        scannedText = findViewById(R.id.scannedContent);


        //Expiry date
        expiryDateText = findViewById(R.id.expiryDateText);
        expiryDateText.setInputType(InputType.TYPE_NULL);
        expiryDateText.setFocusable(false);

        //Dodaj karte do bazy danych button
        btnaddCardToDB = findViewById(R.id.addCardToDB);
        btnaddCardToDB.setEnabled(false);
        btnaddCardToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddCardActivity.this);
                builder.setMessage("Are you sure barcode number is correct?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new CardModel(shplistSpinner.getSelectedItem().toString(), scannedText.getText().toString(), Date.valueOf(formattedDate));
                                goBackToMain();
                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();




            }
        });

        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(AddCardActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ONE_D_CODE_TYPES); //ONE_D_CODE_TYPES WCZYTUJE TYLKO BARCODE
                                                                                                // || ALL_CODE_TYPES -> WCZYTUJE NAWET QR CODE
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                //intentIntegrator.setCaptureActivity()
                //intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                if((expiryDateText.getText() != null) && (scannedText.getText() != null))
                {
                    btnaddCardToDB.setEnabled(true);
                }



            }
        };
        scannedText.addTextChangedListener(scannedTextChange);


        expiryDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                //picker dialog
                if(picker[0] == null)
                {
                    picker[0] = new DatePickerDialog(AddCardActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    expiryDateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    formattedDate = "" + year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                                }
                            }, year, month, day);
                    picker[0].getDatePicker().setMinDate(cldr.getTimeInMillis());
                    picker[0].show();
                }
                else
                {
                    picker[0].getDatePicker().setMinDate(cldr.getTimeInMillis());
                    picker[0].show();
                }


            }

        });

    }

    //Metody odpowiedzialne za pobieranie syfu z bazy odnośnie sklepów.

    //Przenieść go na start aplikacji aby pobierać wszystko za jednym zamachem!!


    public List<String> getAllLabels()
    {
        List<String> labels = new ArrayList<String>();
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Name FROM Shops");
            while (resultSet.next()){
                labels.add(resultSet.getString("Name"));
            }

        } catch (Exception ex) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
            return labels;
        }
    }
    private void loadSpinnerData() {

        // Spinner Drop down elements
        List<String> lables = getAllLabels();

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        shplistSpinner.setAdapter(dataAdapter);
    }


    void goBackToMain()
    {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "Aborted scanning", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Sucesfully Scanned" , Toast.LENGTH_SHORT).show();
                scannedText.setText(String.format("%s", Result.getContents()));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}