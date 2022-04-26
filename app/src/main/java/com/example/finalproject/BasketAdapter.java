package com.example.finalproject;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class BasketAdapter extends BaseAdapter {


    private LayoutInflater infltr;
    private Context context;
    private Context mContext;
    private LayoutInflater inflter;
    ArrayList<BasketItem> baItem;
    private androidx.localbroadcastmanager.content.LocalBroadcastManager LocalBroadcastManager;

    public BasketAdapter(Context applicationContext, ArrayList<BasketItem> baItem) {
        this.context = applicationContext;

        this.mContext=applicationContext;

        this.baItem = baItem;

        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return baItem.size();
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

        view = inflter.inflate(R.layout.orderlayout, null);
        ImageView image = view.findViewById(R.id.order_image);
        TextView name = view.findViewById(R.id.ordered_item_name);
        TextView price = view.findViewById(R.id.ordered_item_price);
        TextView qty = view.findViewById(R.id.ordered_item_qty);

        final ViewGroup vg = viewGroup;
        name.setText(baItem.get(i).getName());
        image.setImageResource(view.getResources().getIdentifier(baItem.get(i).getImage(), "drawable", context.getPackageName()));
        price.setText(baItem.get(i).getPrice());
        qty.setText(String.valueOf(baItem.get(i).getQty()));

        // increment quantity of item in basket

        Button increment = view.findViewById(R.id.increment);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(qty.getText().toString());
                int stock_quantity= newdb.stockquantity(baItem.get(i).getId());
           if (num<stock_quantity)
           {
               num++;
               qty.setText(String.valueOf(num));
               try {
                   baItem.get(i).setQty(num);
               } catch (Exception ex) {
                   Toast.makeText(
                           context, ex.getMessage(), Toast.LENGTH_SHORT).show();
               }
               if (mContext instanceof BasketActivity) {
                   ((BasketActivity) mContext).calculatePrice();
               }
           }
           else
           {
               Toast.makeText(context,baItem.get(i).getName()+" only "+stock_quantity+" available", Toast.LENGTH_SHORT).show();
           }
            }
        });

        // decrement quantity of item in basket

        Button decrement = view.findViewById(R.id.decrement);
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(qty.getText().toString());
                num--;
                if (num > 0) {
                    qty.setText(String.valueOf(num));
                    try {
                        baItem.get(i).setQty(num);
                    } catch (Exception ex) {
                        Toast.makeText(
                                context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    baItem.remove(i);
                    notifyDataSetChanged();
                }
                if (context instanceof BasketActivity) {
                    ((BasketActivity) context).recreate();
                }
            }
        });




        return view;
    }
}


