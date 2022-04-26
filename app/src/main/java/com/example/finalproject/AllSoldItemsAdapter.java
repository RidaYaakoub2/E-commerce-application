package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AllSoldItemsAdapter extends BaseAdapter {

  private final LayoutInflater inflter;

    private Context context;
    ArrayList<vieworderrow> itemslist;
    private  int status;
    private int user_id;
    public AllSoldItemsAdapter(Context context , ArrayList<vieworderrow> orderslist,int status,int user_id)
    {
        this.status=status;
        this.user_id=user_id;
        this.context=context;
        this.itemslist=orderslist;
        inflter = (LayoutInflater.from(context));
    }





    @Override
    public int getCount() {
        return itemslist.size();
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
    ///if(user_id==1) {
        view = inflter.inflate(R.layout.userviewlayout, null);
        TextView order_num = view.findViewById(R.id.ordernumber);


        TextView username = view.findViewById(R.id.customername);
        TextView num_items = view.findViewById(R.id.Num_itms_per_order);
        TextView orderprice = view.findViewById(R.id.OrderTotalprice);

        final ViewGroup vg = viewGroup;
        int num_row = i + 1;
        order_num.setText(String.valueOf(num_row));
        username.setText(itemslist.get(i).getName());
        num_items.setText(itemslist.get(i).getNum_items());
        orderprice.setText(itemslist.get(i).getTotalprice() + " $");

        return view;
    }
}
