package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CatActivity extends AppCompatActivity {
    private Dbhelper newdb;
    private ArrayList<Category> customCatlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        initdb();
        populateCat();
        GetStatus();
        showbtn(GetStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);

        // show admin or customer menu

      if(status==1)
      {
          MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.adminmenu, menu);
          return true;
      }
      else
          {
          MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.menu, menu);
          return true;
      }
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.ShowitemAct:
                Intent z = new Intent(CatActivity.this,ItemsActivity.class);
                z.putExtra("status",GetStatus());
                z.putExtra("userid",Getuserid());
                startActivity(z);
                return true;
            case R.id.basket:
                //   Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CatActivity.this,BasketActivity.class);
                i.putExtra("userid",Getuserid());
                startActivity(i);
                return true;
            case R.id.logout:
                Intent l = new Intent(CatActivity.this,MainActivity.class);
                l.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                return true;
            case R.id.vieworders:

                Intent m = new Intent(CatActivity.this,ViewOrdersActivity.class);
                m.putExtra("userid",Getuserid());
                m.putExtra("status",GetStatus());
                startActivity(m);
                return true;

            case R.id.ShowCatAct:
                Toast.makeText(getApplicationContext(),"You are already in category page",Toast.LENGTH_LONG).show();
                return true;

           case R.id.viewSolditems:

                Intent alo= new Intent(CatActivity.this,AllSoldItemsView.class);
                alo.putExtra("userid",Getuserid());
                alo.putExtra("status",GetStatus());
                startActivity(alo);

                return true;
            case R.id.ViewLogin:
                Intent alm=new Intent(CatActivity.this,TransactionsViewing.class);
                alm.putExtra("LogReg/Others","LogReg");
                alm.putExtra("userid",Getuserid());
                startActivity(alm);

                return true;
            case R.id.ViewCrud:
                Intent crud = new Intent(CatActivity.this,TransactionsViewing.class);
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
        Dbhelper newdb = new Dbhelper(getApplicationContext());
    }

    // Populate data of categories in category page

    public void populateCat()
    {
        Dbhelper newdb = new Dbhelper(getApplicationContext());
        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);

            ListView lsv = findViewById(R.id.listview);

            customCatlist = newdb.GetCat();

            CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), customCatlist, status,Getuserid());
            lsv.setAdapter(adapter);
            lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category cat = customCatlist.get(i);

                  Intent c = new Intent(CatActivity.this, ItemsActivity.class);
                  c.putExtra("status",status);
                  c.putExtra("userid",Getuserid());
                  c.putExtra("catID", cat.getId());

                  startActivity(c);
              }
          });
    }


   public void openAddAct(View view)
   {
       Intent intent = getIntent();
       int status = intent.getIntExtra("status",0);
       Intent n =new Intent(CatActivity.this,EditActivity.class);
       n.putExtra("status",status);
       n.putExtra("EditOrAdd","Add");
       n.putExtra("ItemOrCat","Cat");
       n.putExtra("userid",Getuserid());
       startActivity(n);
   }
// Get status
    public int GetStatus()
    {
        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);

        if(status==1)
            return 1;
        else
            return 2;
    }
// Get UserId
    public  int Getuserid()
    {
        Intent intent = getIntent();
        int user_id = intent.getIntExtra("userid",0);

       return user_id;
    }


// Add button show it only in Admin site
    public  void showbtn(int status) {
        ImageView Add = findViewById(R.id.AddnewCat);

        if (status == 1) {
            Add.setVisibility(View.VISIBLE);
        } else
        {

            Add.setVisibility(View.GONE);
        }
    }
}