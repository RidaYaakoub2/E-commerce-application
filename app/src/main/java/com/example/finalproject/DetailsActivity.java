package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Dbhelper newdb = new Dbhelper(getApplicationContext());

        Intent m = getIntent();
       int id = m.getIntExtra("id",0);
       String name = m.getStringExtra("name");
       String price = m.getStringExtra("price");
       String imagename = m.getStringExtra("image");
       String details = m.getStringExtra("details");

        ImageView imgview = findViewById(R.id.imagedet);
        TextView nameview =findViewById(R.id.namedet);
        TextView priceview =findViewById(R.id.pricedet);
        TextView detview =findViewById(R.id.moredetails);

        imgview.setImageResource(getResources().getIdentifier(imagename,"drawable",getPackageName()));
        nameview.setText(name);
        priceview.setText(price);
        detview.setText(details);



    }
    // Add item to the basket
    public void Buy(View v)
    {
        Intent m = getIntent();
        int id = m.getIntExtra("id",0);
        String name = m.getStringExtra("name");
        String price = m.getStringExtra("price");
        String imagename = m.getStringExtra("image");

        if( BasketActivity.CheckExistingItem(name) == 1)
        {
            Toast.makeText(getApplicationContext(), "Product Already In The Basket", Toast.LENGTH_SHORT).show();
        }

        else {
            BasketItem BIt = new BasketItem(id, imagename, name, price, 1);
            BasketActivity.BaItem.add(BIt);
            Toast.makeText(getApplicationContext(), "Product is added", Toast.LENGTH_SHORT).show();
        }
    }

}