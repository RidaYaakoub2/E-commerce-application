package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class CRUDAdapter extends BaseAdapter {
    private LayoutInflater inflter;

    private Context context;
    private  ArrayList<CRUDTran> CRUDTran;
    private String type;
    private int user_id;
    private androidx.localbroadcastmanager.content.LocalBroadcastManager LocalBroadcastManager;

    public CRUDAdapter(Context applicationContext,ArrayList<CRUDTran> CRUDTran, String type,int user_id) {
        this.context = applicationContext;

        this.user_id=user_id;

        this.type=type;

        this.CRUDTran = CRUDTran;

        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return CRUDTran.size();
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
        Dbhelper newdb = new Dbhelper(context);
        String actdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        final ViewGroup vg = viewGroup;

        view = inflter.inflate(R.layout.transactionviewdata, null);
        TextView username = view.findViewById(R.id.Tranusername);
        TextView action = view.findViewById(R.id.ActionMade);
        TextView date = view.findViewById(R.id.TranDate);

        username.setText(CRUDTran.get(i).getUname());

        switch (CRUDTran.get(i).getActionmade()) {

            case 2:
                //        action.setTextColor(Color.("purple_200"));;
                action.setText("Added New Category");
                break;
            case 3:
                //       action.setTextColor(Color.parseColor("teal_700"));;
                action.setText("Added New Item");
                break;
            case 4:
                //       action.setTextColor(Color.parseColor("red"));;
                action.setText("Edited "+CRUDTran.get(i).getAplied_name());
                break;
            case 5:
                //       action.setTextColor(Color.parseColor("red"));;
                action.setText("Edited item "+CRUDTran.get(i).getAplied_name());
                break;
            case 6:
                //       action.setTextColor(Color.parseColor("red"));;
                action.setText("Delete  "+CRUDTran.get(i).getAplied_name());
                break;
            case 7:
                //       action.setTextColor(Color.parseColor("red"));;
                action.setText("Delete item "+CRUDTran.get(i).getAplied_name());
                break;
            case 8:
                //       action.setTextColor(Color.parseColor("red"));;
                action.setText("Delete Order "+CRUDTran.get(i).getAplied_name());
                break;
            case 9:
                //       action.setTextColor(Color.parseColor("red"));;
                action.setText("Delete LoginHistory "+CRUDTran.get(i).getAplied_name());
                break;
        }
        date.setText(CRUDTran.get(i).getDate());
        Button btndelete = view.findViewById(R.id.CRUDDelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder bld = new AlertDialog.Builder(vg.getContext());
                bld
                        .setTitle("Deleting CRUD transaction")
                        .setMessage("Are you sure you want to delete this transaction?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id =CRUDTran.get(i).getCrudid();
                                CRUDTran.remove(i);
                                newdb.DeleteCrud(id);
                                notifyDataSetChanged();
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
