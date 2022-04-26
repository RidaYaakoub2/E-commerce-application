package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllSoldItemsView extends AppCompatActivity {

    private ListView lv_items;
    private ArrayList<vieworderrow> itemslist;
    private Dbhelper newdb;
    private AllSoldItemsAdapter adapter;
    private ListView lv_loyal;
    private ArrayList<vieworderrow> loyal_list;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);


        Dbhelper newdb = new Dbhelper(getApplicationContext());

        lv_items = findViewById(R.id.AllItemsSold);

        itemslist = new ArrayList<vieworderrow>();
    //    loyal_list= new ArrayList<vieworderrow>();
        TextView profit = findViewById(R.id.profit);
        profit.setText("Profit:"+newdb.GetProfit()+" $");

        Intent intent = getIntent();
        int catID = intent.getIntExtra("catID",0);
        int status = intent.getIntExtra("status",0);
        int userid =intent.getIntExtra("userid",0);

        populateAllItems(catID);
      //  populateLoyal(2);
    }

  // Getting all sold items with quantity
    public void populateAllItems(int userid)
    {
        Dbhelper newdb = new Dbhelper(getApplicationContext());
        Intent intent = getIntent();

        int status = intent.getIntExtra("status",0);
        try {
            itemslist = newdb.AdminViewAllSoldItems();
        }
        catch (Exception ex) {

            Toast.makeText(
                    this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        };

        adapter= new AllSoldItemsAdapter (getApplicationContext(),itemslist,status,userid);

        lv_items.setAdapter(adapter);
    }

}