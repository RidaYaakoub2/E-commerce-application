package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CategoryAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Category> Category;
    private final LayoutInflater inflter;
    private final int status;
    private int user_id = 0;

    public CategoryAdapter(Context applicationContext, ArrayList<Category> Category, int status,int user_id) {
        this.status = status;
        this.user_id=user_id;
        this.context = applicationContext;
        this.Category = Category;
        inflter = (LayoutInflater.from(applicationContext));


    }

    @Override
    public int getCount() {
        return Category.size();
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

        if (status == 1) {
            view = inflter.inflate(R.layout.categorycontrolpanel, null);

            ImageView img = view.findViewById(R.id.A_imageView);
            TextView tv_name = view.findViewById(R.id.A_tv_name);

            final ViewGroup vg = viewGroup;

         //   img.setImageResource(R.drawable.ic_launcher_background);
            try {
                img.setImageResource(view.getResources().getIdentifier(Category.get(i).getCatimage(), "drawable", context.getPackageName()));
            }
            catch (Exception ex) {

                Toast.makeText(
                        context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            tv_name.setText(Category.get(i).getCatname());
            int id = Category.get(i).getId();

            // Delete Button of Category

            Button btndelete = view.findViewById(R.id.Delete);
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder bld = new AlertDialog.Builder(vg.getContext());
                    bld
                            .setTitle("Deleting Category")
                            .setMessage("Are you sure you want to delete this item?")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                String name =Category.get(i).getCatname();
                                   Category.remove(i);
                                  newdb.DeleteCatById(id);
                                   notifyDataSetChanged();
                                  newdb.NewTransaction(user_id,date,6,name);

                                }
                            });

                    AlertDialog dialog = bld.create();

                    //show it
                    dialog.show();
                }
            });

          // Edit button of Category
            Button btnedit = view.findViewById(R.id.Edit);
            btnedit.setOnClickListener(new View.OnClickListener() {
                private LayoutInflater getLayoutinflater;

                @Override
                public void onClick(View view) {


                    Intent s = new Intent(context,EditActivity.class);
                    s.putExtra("name",Category.get(i).getCatname());
                    s.putExtra("status",status);
                    s.putExtra("EditOrAdd","Edit");
                    s.putExtra("ItemOrCat","Cat");
                    s.putExtra("catid",Category.get(i).getId());
                    s.putExtra("userid",user_id);
                    s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(s);

                }


            });
        }
        else
        {
            view = inflter.inflate(R.layout.categoryview, null);

            ImageView img = view.findViewById(R.id.U_imageView);
            TextView tv_name = view.findViewById(R.id.U_tv_name);

            final ViewGroup vg = viewGroup;

            try {
                img.setImageResource(view.getResources().getIdentifier(Category.get(i).getCatimage(), "drawable", context.getPackageName()));
            }
            catch (Exception ex) {

                Toast.makeText(
                        context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            tv_name.setText(Category.get(i).getCatname());
        }
        return view;
    }
}

