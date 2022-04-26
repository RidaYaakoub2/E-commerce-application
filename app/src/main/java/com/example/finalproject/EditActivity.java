package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
       ShowbuttonsTextview();

        Intent intent = getIntent();
        String action = intent.getStringExtra("EditOrAdd");
        String type = intent.getStringExtra("ItemOrCat");
        int user_id=intent.getIntExtra("userid",0);
        String getname = intent.getStringExtra("name");
        EditText cname = findViewById(R.id.editTextCatName);
        cname.setText(getname);
    }
    public void AddNewCat(View v)
    {

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Intent intent = getIntent();
        int status= intent.getIntExtra("status",0);
        int user_id=intent.getIntExtra("userid",0);
        EditText catname = findViewById(R.id.AddValCat);
        String cname = catname.getText().toString();
        //  Toast.makeText(getApplicationContext(), catname, Toast.LENGTH_SHORT).show();
        Dbhelper newdb = new Dbhelper(getApplicationContext());
        newdb.AddnewCat(cname);
        Intent n = new Intent(EditActivity.this,CatActivity.class);
        n.putExtra("status",status);
        n.putExtra("userid",user_id);
        startActivity(n);
        newdb.NewTransaction(user_id,date,2,cname);




    }



    public void UpdateCat(View v) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Intent intent = getIntent();
        int id = intent.getIntExtra("catid", 0);
        int status = intent.getIntExtra("status", 0);
        String action = intent.getStringExtra("EditorAdd");
        String type = intent.getStringExtra("ItemOrCat");
        int user_id=intent.getIntExtra("userid",0);

                EditText cname = findViewById(R.id.editTextCatName);
                String catname = cname.getText().toString();
                //  Toast.makeText(getApplicationContext(), catname, Toast.LENGTH_SHORT).show();
                Dbhelper newdb = new Dbhelper(getApplicationContext());
                newdb.editcat(catname, id);
                newdb.NewTransaction(user_id,date,4,catname);
                Intent n = new Intent(EditActivity.this, CatActivity.class);
                n.putExtra("status", status);
                n.putExtra("userid",user_id);
                startActivity(n);



        }


    public void ShowbuttonsTextview()
    {

        Intent intent = getIntent();
        String action = intent.getStringExtra("EditOrAdd");
        String type = intent.getStringExtra("ItemOrCat");
        TextView title=findViewById(R.id.Title);

        // Cat buttons and textview
        Button addbtn = findViewById(R.id.AddNewCat);
        EditText addview = findViewById(R.id.AddValCat);
        EditText updateview = findViewById(R.id.editTextCatName);
        Button updatebtn = findViewById(R.id.updatebtn2);

        //Items buttons and textview

        Button addItembtn=findViewById(R.id.AddNewItem);
        Button updateItemBtn =findViewById(R.id.updateItembtn);
        EditText itemname = findViewById(R.id.ItemViewName);
        EditText quantity = findViewById(R.id.ItemViewQuantity);
        EditText price = findViewById(R.id.ItemViewPrice);
        EditText details =findViewById(R.id.DetailsTextView);

        if (type.equals("Cat"))
        {
            addItembtn.setVisibility(View.INVISIBLE);
            updateItemBtn.setVisibility(View.INVISIBLE);
            itemname.setVisibility(View.INVISIBLE);
            quantity.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);
           details.setVisibility(View.INVISIBLE);
            if (action.equals("Add"))
            {


                title.setText("Add New Category");

                 updateview.setVisibility(View.INVISIBLE);
                 updatebtn.setVisibility(View.INVISIBLE);

            }
            else
            {
                title.setText("Edit Category");
                addbtn.setVisibility(View.INVISIBLE);
                addview.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            updateview.setVisibility(View.INVISIBLE);
            updatebtn.setVisibility(View.INVISIBLE);
            addbtn.setVisibility(View.INVISIBLE);
            addview.setVisibility(View.INVISIBLE);

            if (action.equals("Add"))
            {
                title.setText("Add New Item");
                updateItemBtn.setVisibility(View.INVISIBLE);
                itemname.setText("Item name");
                quantity.setText("Item quantity");
                price.setText("Item price");
                details.setText("Item details");
            }
            else
            {
                title.setText("Edit Item");
                String name = intent.getStringExtra("name");
                String itemprice =intent.getStringExtra("price");
                String qty =intent.getStringExtra("quantity");
                String itemdetails=intent.getStringExtra("details");
                addItembtn.setVisibility(View.INVISIBLE);
                itemname.setText(name);
                quantity.setText(qty);
                price.setText(itemprice);
                details.setText(itemdetails);

            }

        }
    }

    public void UpdateItem(View v)
    {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Intent intent = getIntent();
        int cat_id = intent.getIntExtra("catID", 0);
        int status = intent.getIntExtra("status", 0);
        int itemid=intent.getIntExtra("item_id",0);
        int user_id=intent.getIntExtra("userid",0);

        EditText itemname = findViewById(R.id.ItemViewName);
        EditText quantity = findViewById(R.id.ItemViewQuantity);
        EditText price = findViewById(R.id.ItemViewPrice);
        EditText details =findViewById(R.id.DetailsTextView);
        String Itname=  itemname.getText().toString();
        String Itqty=  quantity.getText().toString();
        String Itprice= price.getText().toString();
        String Itdetails=  details.getText().toString();
  //      Toast.makeText(getApplicationContext(),String.valueOf(itemid) , Toast.LENGTH_SHORT).show();
        Dbhelper newdb = new Dbhelper(getApplicationContext());
        try {
            newdb.edititem(Itname, Itqty, Itprice, cat_id, Itdetails, itemid);
        }
        catch (Exception ex) {

            Toast.makeText(
                    getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Intent n = new Intent(EditActivity.this, ItemsActivity.class);
        n.putExtra("status", status);
        n.putExtra("userid",user_id);
        n.putExtra("catID",cat_id);
        startActivity(n);
        newdb.NewTransaction(user_id,date,5,Itname);



    }
    public void AddNewItem(View v)
    {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Intent intent = getIntent();
        int cat_id = intent.getIntExtra("catID", 0);
        int user_id=intent.getIntExtra("userid",0);
        int status = intent.getIntExtra("status", 0);

        EditText itemname = findViewById(R.id.ItemViewName);
        EditText quantity = findViewById(R.id.ItemViewQuantity);
        EditText price = findViewById(R.id.ItemViewPrice);
        EditText details =findViewById(R.id.DetailsTextView);
        String Itname=  itemname.getText().toString();
        String Itqty=  quantity.getText().toString();
        String Itprice= price.getText().toString();
        String Itdetails=  details.getText().toString();

        Dbhelper newdb = new Dbhelper(getApplicationContext());
        newdb.AddnewItem(Itname,Itqty,Itprice,cat_id,Itdetails);
        newdb.NewTransaction(user_id,date,3,Itname);
        Intent n = new Intent(EditActivity.this, ItemsActivity.class);
        n.putExtra("status", status);
        n.putExtra("catID",cat_id);
        n.putExtra("userid",user_id);
        startActivity(n);





    }


}
