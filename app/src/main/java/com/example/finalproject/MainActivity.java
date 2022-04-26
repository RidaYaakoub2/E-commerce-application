package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Dbhelper newdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));

        initdb();
        Register();
    }

    public void initdb()
    {
       Dbhelper newdb = new Dbhelper(getApplicationContext());
    }

    // Check existing of user before login and define the type of user
    public void Login(View v) {

        Dbhelper newdb = new Dbhelper(getApplicationContext());

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        EditText EditName = findViewById(R.id.editName);
        EditText EditPwd = findViewById(R.id.editPassword);
        String uname = EditName.getText().toString();
        String pwd = EditPwd.getText().toString();
        try {
            if (uname.equals(""))
            {
                EditName.setError("Please enter your username");

            }
            else if (pwd.equals(""))
            {
                EditPwd.setError("Please enter your  password");
            }
            else if (newdb.CheckLogin(uname, pwd)==0)
            {
                EditName.setError("Please enter your correct username");
                EditPwd.setError("Please enter your correct password");
                newdb.Login_Register(uname,date,3);
            }
            else
            {
                int user_id =newdb.CheckLogin(uname, pwd);
                if (newdb.DefineUser(uname,pwd)==1)
                {
                    int status = 1;
                    Intent m;
                    m = new Intent(MainActivity.this, CatActivity.class);
                    m.putExtra("status",status) ;
                    m.putExtra("userid",user_id);
                    startActivity(m);
                }
                else
                {
                    int status = 2;
                    Intent m;
                    m = new Intent(MainActivity.this, CatActivity.class);
                    m.putExtra("status",status);
                    m.putExtra("userid",user_id);
                    startActivity(m);
                }

            //    Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
                  newdb.Login_Register(uname,date,2);
            }
        }
        catch (Exception ex) {

            Toast.makeText(
                    this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    // Clickable textview to go to Register Activity
    public void Register() {
        final TextView register = (TextView) findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(m);
            }
        });
    }

}