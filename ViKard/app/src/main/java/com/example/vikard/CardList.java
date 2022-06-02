package com.example.vikard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.vikard.data.LoginRepository;
import com.example.vikard.data.SQLConnection;
import com.example.vikard.data.Session.CardSession;
import com.example.vikard.data.Session.ShopSession;
import com.example.vikard.data.model.CardModel;
import com.example.vikard.data.model.ShopModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CardList extends Fragment {

    ArrayList<CardModel> cardCollection = new ArrayList<>();
    private View rootView;
    private int user_id;
    CardSession cardSession;
    ShopSession ss;
    public CardList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_card_list, container, false);
        user_id = Integer.valueOf(LoginRepository.user.getUserId());
        cardSession = new CardSession(getActivity().getApplicationContext());
        ss = new ShopSession(getActivity().getApplicationContext());

        if(ss.isSaved() && cardSession.isSaved())
        {
            Bundle result = new Bundle();
            ArrayList<Integer> arr = new ArrayList<>();
            cardCollection = cardSession.loadData();
            ArrayList<ShopModel> shopModels = ss.loadData();
            FragmentManager fm = getParentFragmentManager();
            int size = cardCollection.size();
            for (int i = 0; i < size; i++) {
                arr.add(cardCollection.get(i).getShopsId());
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                CardListElement fm2 = new CardListElement(shopModels.get(i), cardCollection.get(i).getUsersCategory(), cardCollection.get(i).getId());
                fm.beginTransaction().add(R.id.card_list,fm2).commit();
                fragmentTransaction.commit();
            }
            result.putIntegerArrayList("123", arr);
            getParentFragmentManager().setFragmentResult("123", result);
        }
        else
            bindCardList();


        return rootView;
    }



    public void populateCardCollection(int UsersId) {
        SQLConnection sql = new SQLConnection();
        Connection conn = sql.getConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Id FROM Cards WHERE UsersId=" + UsersId);
            while (resultSet.next()) {
                cardCollection.add(new CardModel(resultSet.getInt("Id"), false));
            }
        } catch (Exception ex) {
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public void bindCardList() {

        new MyAsyncTask(getActivity()).execute("");
    }


    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;
        final ProgressDialog dialog = new ProgressDialog(getContext(),R.style.MyTheme);

        public MyAsyncTask(Activity contex) {
            this.mContex = contex;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//            dialog.setMessage("Processing...");
            dialog.show();
        }


        protected String doInBackground(String... params) {
            populateCardCollection(user_id);

            ArrayList<ShopModel> shopmodelers = new ArrayList<>();
            FragmentManager fm = getParentFragmentManager();

            Bundle result = new Bundle();
            ArrayList<Integer> arr = new ArrayList<>();
            int size = cardCollection.size();
            for (int i = 0; i < size; i++) {
                ShopModel shopModel = new ShopModel(cardCollection.get(i).getShopsId(), false);
                arr.add(cardCollection.get(i).getShopsId());
                shopmodelers.add(shopModel);

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                CardListElement fm2 = new CardListElement(shopModel, cardCollection.get(i).getUsersCategory(), cardCollection.get(i).getId());
                fm.beginTransaction().add(R.id.card_list, fm2).commit();
                fragmentTransaction.commit();
            }
            result.putIntegerArrayList("123",arr);
            getParentFragmentManager().setFragmentResult("123", result);

            cardSession.createCardSession();
            cardSession.saveData(cardCollection);

            ss.createShopSession();
            ss.saveData(shopmodelers);


            return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            {

                Bundle result1 = new Bundle();
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                builder.setMessage("Check for Your available discounts in Discounts tab");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });



                androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();
                dialog.dismiss();

            }

        }
    }


}