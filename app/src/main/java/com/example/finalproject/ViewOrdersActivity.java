package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewOrdersActivity extends AppCompatActivity {

    private ListView lv_orders;
    private ArrayList<vieworderrow> orderlist;
    private Dbhelper newdb;
    private ViewOrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders2);

        initdb();
        lv_orders = findViewById(R.id.ViewOrderlsv);
        orderlist = new ArrayList<vieworderrow>();

        Intent intent = getIntent();
        int catID = intent.getIntExtra("catID",0);
        int status = intent.getIntExtra("status",0);
        int userid =intent.getIntExtra("userid",0);


        TextView username =findViewById(R.id.VOName);
        TextView numitems=findViewById(R.id.VONumberOfItems);
        TextView titletotalprice =findViewById(R.id.VOTotalPrice);
        TextView numberorder=findViewById(R.id.VONumber);

        // if user type is cutomer sho him his previous orders
        if(status==2)
        {
            PopulateUserOrders(userid);
        }
        // user type is admin show him all orders done
        else
        {
            PopulateAllOrders(userid);
        }

    }

    public void initdb()
    {
        Dbhelper newdb = new Dbhelper(getApplicationContext());
    }
    public void PopulateUserOrders(int userid)
    {
        Dbhelper newdb = new Dbhelper(getApplicationContext());
        Intent intent = getIntent();

        int status = intent.getIntExtra("status",0);
        try {
            orderlist = newdb.CustomerViewOrder(userid);
        }
        catch (Exception ex) {

            Toast.makeText(
                    this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        adapter=new ViewOrdersAdapter(getApplicationContext(),orderlist,status,userid);

        lv_orders.setAdapter(adapter);
    }
    public void PopulateAllOrders(int userid)
    {
        Dbhelper newdb = new Dbhelper(getApplicationContext());
        Intent intent = getIntent();

        int status = intent.getIntExtra("status",0);
        try {
            orderlist = newdb.AdminViewAllOrders();
        }
        catch (Exception ex) {

            Toast.makeText(
                    this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        adapter=new ViewOrdersAdapter(getApplicationContext(),orderlist,status,userid);

        lv_orders.setAdapter(adapter);
    }





}