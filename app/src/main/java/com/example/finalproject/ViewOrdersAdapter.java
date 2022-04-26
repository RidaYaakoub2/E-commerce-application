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

public class ViewOrdersAdapter extends BaseAdapter {
    private final LayoutInflater inflter;
    private Context context;
    ArrayList<vieworderrow> orderslist;
    private  int status;
    private int user_id;
    public ViewOrdersAdapter(Context context , ArrayList<vieworderrow> orderslist,int status,int user_id)
    {
        this.status=status;
        this.user_id=user_id;
        this.context=context;
        this.orderslist=orderslist;
        inflter = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return orderslist.size();
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

        // If user type is admin status = 2 then show him all order done by him
        if (status==2)
       {
            view = inflter.inflate(R.layout.userviewlayout, null);
            TextView order_num = view.findViewById(R.id.ordernumber);
            TextView username = view.findViewById(R.id.customername);
            TextView num_items = view.findViewById(R.id.Num_itms_per_order);
            TextView orderprice = view.findViewById(R.id.OrderTotalprice);

            final ViewGroup vg = viewGroup;
           try {
               int num_row = i + 1;
               order_num.setText(String.valueOf(num_row));

           }
           catch (Exception ex) {
               Toast.makeText(
                       context, ex.getMessage(), Toast.LENGTH_LONG).show();
           }
               username.setText(orderslist.get(i).getName());
            num_items.setText(orderslist.get(i).getNum_items());
            orderprice.setText(orderslist.get(i).getTotalprice()+" $");

       }
        // If user type is customer status = 1 then show him all orders done
       else
        {
            Dbhelper newdb = new Dbhelper(context);
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            view = inflter.inflate(R.layout.admincontrolallorders, null);
            TextView order_num = view.findViewById(R.id.Adminordernumber);
            TextView username = view.findViewById(R.id.Admincustomername);
            TextView num_items = view.findViewById(R.id.AdminNum_itms_per_order);
            TextView orderprice = view.findViewById(R.id.AdminOrderTotalprice);

            final ViewGroup vg = viewGroup;
try {
    int num_row = i + 1;
    order_num.setText(String.valueOf(num_row));
}
catch (Exception ex) {
    Toast.makeText(
            context, ex.getMessage(), Toast.LENGTH_LONG).show();
}
            username.setText(orderslist.get(i).getName());
            num_items.setText(orderslist.get(i).getNum_items());
            orderprice.setText(orderslist.get(i).getTotalprice()+" $");

            Button delete = view.findViewById(R.id.AdminDelteorderbtn);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder bld = new AlertDialog.Builder(vg.getContext());
                    bld
                            .setTitle("Deleting order of " + orderslist.get(i).getName())
                            .setMessage("Are you sure you want to delete this order?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int orderId = orderslist.get(i).getOrder_id();
                                    String name =orderslist.get(i).getName();
                                    orderslist.remove(i);

                                    try {
                                        newdb.DeleteOrderById(orderId);
                                    }

                                    catch (Exception ex) {
                                        Toast.makeText(
                                                context, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    notifyDataSetChanged();
                                    newdb.NewTransaction(user_id,date,8,name);

                                }
                            });

                    AlertDialog dialog = bld.create();

                    //show it
                    dialog.show();
                }
            });

        }






        return view;
    }
}
