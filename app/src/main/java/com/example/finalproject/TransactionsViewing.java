package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TransactionsViewing extends AppCompatActivity {
    private   ArrayList<transaction> traItem  ;
    private   ArrayList<CRUDTran> CRUDTran  ;
    private ListView listtran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_viewing);


        traItem =new ArrayList<transaction>();
        CRUDTran = new ArrayList<CRUDTran>();
        listtran=findViewById(R.id.lsvtran);

        Intent intent = getIntent();
        String type = intent.getStringExtra("LogReg/Others");
        int user_id=intent.getIntExtra("userid",0);
      if (type.equals("LogReg"))
      {
        PopulateTransaction(type,user_id);
      }
      else
      {
          PopulateOthersTransaction(type,user_id);
      }
    }


    public void PopulateTransaction(String type,int user_id)
    {
        Dbhelper newdb = new Dbhelper(getApplicationContext());



        traItem=newdb.ViewLogin_Register();

        TransactionsAdapater adapter =new TransactionsAdapater(getApplicationContext(),traItem,type,user_id);

        listtran.setAdapter(adapter);
    }
    public void PopulateOthersTransaction(String type,int user_id)
    {
        Dbhelper newdb = new Dbhelper(getApplicationContext());


      CRUDTran=newdb.ViewOtherTran();

      CRUDAdapter adapter =new CRUDAdapter(getApplicationContext(),CRUDTran,type,user_id);

        listtran.setAdapter(adapter);
    }
}