package com.example.vikard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.Session.CardSession;
import com.example.vikard.data.Session.ShopSession;
import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.model.NotifyModel;
import com.example.vikard.data.model.ShopModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountActivity extends AppCompatActivity {

    private TextView ShopName;
    private TextView DiscountDescription;

    private CardSession cs;
    private ShopSession ss;
    private NotifyModel nm;
    private ArrayList<CardModel> cardCollection = new ArrayList<>();
    private ArrayList<ShopModel> shopCollection = new ArrayList<>();
    private ArrayList<NotifyModel> notifiesCollection = new ArrayList<>();
    private int user_id;
    private LinearLayout sv;
    private LayoutInflater linflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount_activity);
        user_id = Integer.valueOf(LoginRepository.user.getUserId());

        cs = new CardSession(getApplicationContext());
        ss = new ShopSession(getApplicationContext());




        if (ss.isSaved() && cs.isSaved()) {

            cardCollection = cs.loadData();
            shopCollection = ss.loadData();
            if(cardCollection.size()>0)
                bindCardList();
            else
            {
                sv = findViewById(R.id.DiscountLinearLayout);
                linflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView;
                customView = linflater.inflate(R.layout.discount_list_element, null);
                TextView ad = customView.findViewById(R.id.DiscountDescription);
                TextView be = customView.findViewById(R.id.ContentTitle);
                ad.setText("Check again later");
                be.setText("No promotions available.");
                sv.addView(customView);
            }
        }


    }

    public void bindCardList() {

        new MyAsyncTask().execute("");
    }


    private ArrayList<NotifyModel> getUsersNotifications(int usersId){
        ArrayList<NotifyModel> notifications = new ArrayList<NotifyModel>();
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT Id FROM Notifications WHERE ShopsId IN (SELECT ShopsId FROM Cards WHERE UsersId = ?) LIMIT 10";
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, usersId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                notifications.add(new NotifyModel(resultSet.getInt("Id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
        return notifications;
    }




    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;
        ProgressDialog dialog;

        public MyAsyncTask(Activity contex) {
            this.mContex = contex;
        }

        public MyAsyncTask() {

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DiscountActivity.this,R.style.MyTheme);
            dialog.setCancelable(false);
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//            dialog.setMessage("Processing...");
            dialog.show();
        }


        protected String doInBackground(String... params) {
            notifiesCollection = getUsersNotifications(user_id);
            return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            {

                if(notifiesCollection.size() > 0)
                {
                    sv = findViewById(R.id.DiscountLinearLayout);
                    linflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    int size = notifiesCollection.size();
                    for (int i = 0; i < size; i++) {

                        View customView;
                        customView = linflater.inflate(R.layout.discount_list_element, null);
                        TextView ad = customView.findViewById(R.id.DiscountDescription);
                        TextView be = customView.findViewById(R.id.ContentTitle);

                        ad.setText(notifiesCollection.get(i).getContent());
                        be.setText(notifiesCollection.get(i).getTitle());

                        sv.addView(customView);
                    }
                }
                else
                {
                    sv = findViewById(R.id.DiscountLinearLayout);
                    linflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView;
                    customView = linflater.inflate(R.layout.discount_list_element, null);
                    TextView ad = customView.findViewById(R.id.DiscountDescription);
                    TextView be = customView.findViewById(R.id.ContentTitle);
                    ad.setText("Check again later");
                    be.setText("No promotions available.");
                    sv.addView(customView);
                }
                dialog.dismiss();
            }

        }
    }



}



