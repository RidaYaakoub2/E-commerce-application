package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    public  static ArrayList<BasketItem> BaItem =new ArrayList<BasketItem>() ;
   private float sub_total =0 ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            ShowOrderButton();

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.basket:
                 Toast.makeText(getApplicationContext(), "You are already in Basket page", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.ShowitemAct:
                Intent z = new Intent(BasketActivity.this,ItemsActivity.class);
                z.putExtra("status",GetStatus());
                z.putExtra("userid",Getuserid());
                startActivity(z);
                return true;
            case R.id.ShowCatAct:
                Intent c = new Intent(BasketActivity.this,CatActivity.class);
                c.putExtra("userid",Getuserid());
                c.putExtra("status",GetStatus());
                startActivity(c);
                return true;
            case R.id.logout:
                Intent l = new Intent(BasketActivity.this,MainActivity.class);
                l.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                return true;
            case R.id.vieworders:

                Intent m = new Intent(BasketActivity.this,ViewOrdersActivity.class);
                m.putExtra("userid",Getuserid());
                m.putExtra("status",GetStatus());
                startActivity(m);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);


        ListView lq = findViewById(R.id.BSlistview);

        BasketAdapter adapter = new BasketAdapter(getApplicationContext(),BaItem);

        lq.setAdapter(adapter);
        calculatePrice();


    }



    public static int CheckExistingItem(String name )
    {
        for (int j =0;j<BaItem.size();j++)
        {
            if (BaItem.get(j).getName() ==name)
                return 1;
        }
        return 0;
    }

    // Calculate Total Price

    public void calculatePrice()
    {
        for(int i=0;i<BaItem.size();i++)
        {
            String price =String.valueOf(BaItem.get(i).getPrice()).replace("$","");
             sub_total=sub_total+(Integer.valueOf(price) * BaItem.get(i).getQty());
        }
        TextView tv_total_amount=findViewById(R.id.Total);
        tv_total_amount.setText(sub_total+" $");
    }

   // Order click button
    public void order(View v)
    {
         Toastorder();
    }
    public void Toastorder()
    {
        Dbhelper getdb = new Dbhelper(getApplicationContext());
        int order_id = getdb.GetLastOrder_id();
        order_id++;
        Dbhelper newdb = new Dbhelper(getApplicationContext());

      for(int i=0;i<BaItem.size();i++) {
          int product_id = BaItem.get(i).getId();
          int quantity = BaItem.get(i).getQty();

          newdb.InsertOrders(order_id, Getuserid(), product_id, quantity);
          // decrement the ordered quantity from products

          newdb.UpdateStock(quantity, product_id);
          }
        Toast.makeText(getApplicationContext(), "Your Order Is Sent", Toast.LENGTH_SHORT).show();
      BaItem.clear();

    }

    public void ShowOrderButton()
    {
        if (BaItem.size()==0)
        {
            Button order = findViewById(R.id.Order);
            order.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "You didn't Add any product to the cart ", Toast.LENGTH_SHORT).show();
        }
    }


    public  int Getuserid()
    {
        Intent intent = getIntent();
        int user_id = intent.getIntExtra("userid",0);
        return user_id;
    }


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