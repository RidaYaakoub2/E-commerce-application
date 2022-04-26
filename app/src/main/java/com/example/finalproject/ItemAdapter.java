package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private  ArrayList<item> items;
    private  Context context;
    private  int status;
    private int user_id;
    private int cat_id;

    public ItemAdapter(Context applicationContext, ArrayList<item> items, int status,int user_id,int cat_id) {
        this.status = status;
        this.user_id = user_id;
        this.context = applicationContext;
        this.items = items;
        this.cat_id=cat_id;
        inflter = (LayoutInflater.from(applicationContext));
    }
        @Override
    public int getCount() {
        return items.size();
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
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        //  if status = 1 open admin site else open user site

        if(status == 1)
        {
            view = inflter.inflate(R.layout.itemcontrolpanel, null);

            ImageView img = view.findViewById(R.id.A_I_imageView);
            TextView tv_name =  view.findViewById(R.id.A_tv_Iname);
            TextView tv_price =   view.findViewById(R.id.A_tp_view);
            TextView tv_quantity = view.findViewById(R.id.A_tq_view);


            final ViewGroup vg = viewGroup;


            try {
                img.setImageResource(view.getResources().getIdentifier(items.get(i).getImage(), "drawable", context.getPackageName()));

            }
            catch (Exception ex) {
                        Toast.makeText(
                           context, ex.getMessage(), Toast.LENGTH_LONG).show();
                         }

            tv_name.setText(items.get(i).getName());
            tv_price.setText(items.get(i).getPrice());
            tv_quantity.setText(String.valueOf(items.get(i).getQuantity()));

            // Got to EditActivity and send type of action and if items or category Edit

            Button Edit = view.findViewById(R.id.Edit_item);
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent s = new Intent(context,EditActivity.class);
                    s.putExtra("catID",cat_id);
                    s.putExtra("userid",user_id);
                    s.putExtra("name",items.get(i).getName());
                    s.putExtra("price",items.get(i).getPrice());
                    s.putExtra("quantity",String.valueOf(items.get(i).getQuantity()));
                    s.putExtra("details",items.get(i).getDetails());
                    s.putExtra("status",status);
                    s.putExtra("ItemOrCat","items");
                    s.putExtra("EditOrAdd","Edit");
                    s.putExtra("item_id",items.get(i).getId());

                    s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(s);



                }

            });

            // Delete Item

            int id = items.get(i).getId();
            Button btndelete = view.findViewById(R.id.Delete_item);
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder bld = new AlertDialog.Builder(vg.getContext());
                    bld
                            .setTitle("Deleting " + items.get(i).getName())
                            .setMessage("Are you sure you want to delete this item?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String name = items.get(i).getName();
                                    items.remove(i);

                                    try {
                                        newdb.DeleteItemById(id);
                                    }

                                    catch (Exception ex) {
                                        Toast.makeText(
                                                context, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    notifyDataSetChanged();
                                   newdb.NewTransaction(user_id,date,7,name);

                                }
                            });

                    AlertDialog dialog = bld.create();

                    //show it
                    dialog.show();
                }

            });


        }

        else
        {
            view = inflter.inflate(R.layout.itemview, null);

            ImageView img = view.findViewById(R.id.imageView);
            TextView tv_name =  view.findViewById(R.id.tv_name);
            TextView tv_price =   view.findViewById(R.id.tv_price);


            final ViewGroup vg = viewGroup;

     try {
         img.setImageResource(view.getResources().getIdentifier(items.get(i).getImage(), "drawable", context.getPackageName()));
          }
       catch (Exception ex) {
              Toast.makeText(
                  context, ex.getMessage(), Toast.LENGTH_LONG).show();
              }


            tv_name.setText(items.get(i).getName());
            tv_price.setText(items.get(i).getPrice());

            // Customer Add Items to the basket

            Button BtnAdd = view.findViewById(R.id.Add);
            BtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item itm = items.get(i);

                    if( BasketActivity.CheckExistingItem(itm.getName()) == 1)
                    {
                        try {
                            Toast.makeText(context, "Product Already In The Basket", Toast.LENGTH_SHORT).show();
                            }
                        catch (Exception ex) {

                            Toast.makeText(
                                    context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        BasketItem BIt = new BasketItem(itm.getId(),itm.getImage(), itm.getName(), itm.getPrice(), 1);
                        BasketActivity.BaItem.add(BIt);
                        Toast.makeText(context, "Product is added", Toast.LENGTH_SHORT).show();
                    }
                }
            });
         // Customer View details about item
            Button BtnDetails = view.findViewById(R.id.Details);
            BtnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent s = new Intent(context,DetailsActivity.class);
                    s.putExtra("name",items.get(i).getName());
                    s.putExtra("image",items.get(i).getImage());
                    s.putExtra("price",items.get(i).getPrice());
                    s.putExtra("id",items.get(i).getId());
                    s.putExtra("details",items.get(i).getDetails());
                    s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(s);

                }
            });
            }
        return view;
    }
}
