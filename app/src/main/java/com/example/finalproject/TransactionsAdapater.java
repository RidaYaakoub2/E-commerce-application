package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionsAdapater extends BaseAdapter {
    private LayoutInflater inflter;

    private Context context;
    private final  ArrayList<transaction> TraItem;
    private String type;
    private int user_id;
    private androidx.localbroadcastmanager.content.LocalBroadcastManager LocalBroadcastManager;

    public TransactionsAdapater(Context applicationContext, ArrayList<transaction> TraItem, String type,int user_id) {
        this.context = applicationContext;

        this.type=type;
        this.user_id=user_id;

        this.TraItem = TraItem;

       inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return TraItem.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        String actdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

      view = inflter.inflate(R.layout.transactionviewdata, null);
        Dbhelper newdb = new Dbhelper(context);
      final ViewGroup vg = viewGroup;
      TextView username = view.findViewById(R.id.Tranusername);
      TextView action = view.findViewById(R.id.ActionMade);
      TextView date = view.findViewById(R.id.TranDate);

                  username.setText(TraItem.get(i).getName());
                    switch (TraItem.get(i).getActionmade()) {

                       case 1:
                        //        action.setTextColor(Color.("purple_200"));;
                         action.setText("Register Succesfuly");
                         break;
                       case 2:
                         //       action.setTextColor(Color.parseColor("teal_700"));;
                        action.setText("Login Succesfuly");
                        break;
                       case 3:
                         //       action.setTextColor(Color.parseColor("red"));;
                         action.setText("Tried to login");
                         break;


      }
      date.setText(TraItem.get(i).getDate());

        Button delete = view.findViewById(R.id.CRUDDelete);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder bld = new AlertDialog.Builder(vg.getContext());
                bld
                        .setTitle("Deleting transaction of " + TraItem.get(i).getName())
                        .setMessage("Are you sure you want to delete this item?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = TraItem.get(i).getHistory_id();
                                String name = TraItem.get(i).getName();
                                TraItem.remove(i);

                                try {
                                    newdb.DeleteLoginHistory(id);
                                }

                                catch (Exception ex) {
                                    Toast.makeText(
                                            context, ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                notifyDataSetChanged();
                                newdb.NewTransaction(user_id,actdate ,9,name);

                            }
                        });

                AlertDialog dialog = bld.create();

                //show it
                dialog.show();
            }

        });




        return view;
    }
}
