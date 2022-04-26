package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private String gender;
    private Spinner spinner;
    private boolean spSelection = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.teal_700)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Gender();
    }

    public void Gender()
    {

        spinner = findViewById(R.id.genderspinner);
        List<String> gender = new ArrayList<String>();
        gender.add("Male");
        gender.add("Female");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,gender);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          String  gender =adapterView.getItemAtPosition(i).toString();
          Setgender(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }
    public void Setgender(String gender)
    {
       this.gender = gender;
    }



    // On click button Register

    public void Register(View v)
    {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        Dbhelper newdb = new Dbhelper(getApplicationContext());

        EditText uname =findViewById(R.id.Username);

        EditText password = findViewById(R.id.Password);
        EditText check_pwd=findViewById(R.id.CheckPassword);
        String username = uname.getText().toString();
        String pwd = password.getText().toString();
        String checkpwd = check_pwd.getText().toString();

     // Check if password and repeated one matches
      if(pwd.equals(checkpwd)) {
          newdb.AddNewAccount(username, 2, pwd, gender);
          newdb.Login_Register(username,date,1);
          Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
          Intent m = new Intent(RegisterActivity.this,MainActivity.class );
          startActivity(m);
       }
       else
       {
        check_pwd.setError("Password doesn't Match");
       }

    }
}