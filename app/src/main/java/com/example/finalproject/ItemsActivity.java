package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    private ListView lv_items;
    private ArrayList<item> itemslist;
    private Dbhelper newdb;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        initdb();
        lv_items = findViewById(R.id.itemslv);
        itemslist = new ArrayList<item>();

        Intent intent = getIntent();
        int catID = intent.getIntExtra("catID",0);

        showbtn(GetStatus());
        if (catID>0)
        {
            populateitems(catID);
        }
        else
        {
            populateAllitems();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // show admin or customer menu
        if(GetStatus()==1)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.adminmenu, menu);
            return true;
        }
        else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }
            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.basket:
                //   Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ItemsActivity.this,BasketActivity.class);
                i.putExtra("userid",Getuserid());
                startActivity(i);
                return true;
            case R.id.logout:
                Intent l = new Intent(ItemsActivity.this,MainActivity.class);
                l.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                return true;
            case R.id.vieworders:

                Intent m = new Intent(ItemsActivity.this,ViewOrdersActivity.class);
                m.putExtra("userid",Getuserid());
                m.putExtra("status",GetStatus());
                startActivity(m);
                return true;
            case R.id.ShowitemAct:
                Toast.makeText(getApplicationContext(),"You are already in Items page",Toast.LENGTH_LONG).show();
                return true;
            case R.id.ShowCatAct:
                Intent c = new Intent(ItemsActivity.this,CatActivity.class);
                c.putExtra("userid",Getuserid());
                c.putExtra("status",GetStatus());
                startActivity(c);
                return true;

            case R.id.viewSolditems:

                Intent alo= new Intent(ItemsActivity.this,AllSoldItemsView.class);
                alo.putExtra("userid",Getuserid());
                alo.putExtra("status",GetStatus());
                startActivity(alo);

                return true;
            case R.id.ViewLogin:
                Intent alm=new Intent(ItemsActivity.this,TransactionsViewing.class);
                alm.putExtra("LogReg/Others","LogReg");
                alm.putExtra("userid",Getuserid());
                startActivity(alm);

                return true;
            case R.id.ViewCrud:
                Intent crud = new Intent(ItemsActivity.this,TransactionsViewing.class);
                crud.putExtra("userid",Getuserid());
                crud.putExtra("LogReg/Others","Others");
                startActivity(crud);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initdb()
    {
        newdb = new Dbhelper(getApplicationContext());
    }

    // Gettting data of Items into list
    public void populateitems(int catID)
    {
        try
        {
            itemslist = newdb.GetItemById(catID,GetStatus());

            adapter=new ItemAdapter(getApplicationContext(),itemslist,GetStatus(),Getuserid(),catID);

            lv_items.setAdapter(adapter);
        }
        catch (Exception ex) {
            Toast.makeText(
                    this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void populateAllitems()
    {
        try
        {
            itemslist = newdb.GetAllItems(GetStatus());

            adapter=new ItemAdapter(getApplicationContext(),itemslist,GetStatus(),Getuserid(),0);

            lv_items.setAdapter(adapter);
        }
        catch (Exception ex) {
            Toast.makeText(
                    this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }





    // Getting UserId

    public  int Getuserid()
    {
        Intent intent = getIntent();
        int user_id = intent.getIntExtra("userid",0);

        return user_id;
    }

    // Add button show it only in Admin site
    public  void showbtn(int status) {
        ImageView Add = findViewById(R.id.AddnewItem3);

        if (status == 1) {
            Add.setVisibility(View.VISIBLE);
        } else
        {

            Add.setVisibility(View.GONE);
        }
    }
    public void GotoAdd(View v)
    {
        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);

        int catID = intent.getIntExtra("catID",0);
        Intent n =new Intent(ItemsActivity.this,EditActivity.class);
        n.putExtra("catID",catID);
        n.putExtra("status",status);
        n.putExtra("userid",Getuserid());
        n.putExtra("EditOrAdd","Add");
        n.putExtra("ItemOrCat","Items");
        startActivity(n);
    }
    // Get status if admin or customer
    public int GetStatus()
    {
        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);

        if(status==1)
            return 1;
        else
            return 2;
    }
}