package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "newdb";
    private SQLiteDatabase db;
    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String usql = "create table users" + "(id integer primary key, name text,password text,UserType text)";
        db.execSQL(usql);
        String casql = "create table categories" + "(cat_id integer primary key, cat_iamge text, cat_name text)";
        db.execSQL(casql);
        String itsql = "create table items" + "(item_id integer primary key, item_name text, item_price integer , qty integer,cat_item_id integer,item_image text)";
        db.execSQL(itsql);
        String orsql = "create table orders"+"(table_id integer primary key,order_id integer,order_product_id integer,order_user_id integer)";
        db.execSQL(orsql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS users";
        db.execSQL(sql);
        onCreate(db);
        String casql = "DROP TABLE IF EXISTS categories";
        db.execSQL(casql);
        onCreate(db);
        String itsql = "DROP TABLE IF EXISTS items";
        db.execSQL(itsql);
        onCreate(db);
        String orsql = "DROP TABLE IF EXISTS orders";
        db.execSQL(orsql);
        onCreate(db);

    }

    // Check if username and password exist

    public int CheckLogin(String name, String pwd)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from users where users.name='" + name + "'And users.password='" + pwd + "'";
        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();

        if (cur.getCount() == 0) {
            return 0;
        } else {
            return cur.getInt((cur.getColumnIndex("id")));
        }
    }

    // To know if account that loged in is admin or customer

    public  int DefineUser(String name, String pwd)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select User_type from users where users.name='" + name + "'And users.password='" + pwd + "'";
        Cursor cur = db.rawQuery(sql, null);
        cur.moveToFirst();
        int UserType = Integer.valueOf(cur.getString((cur.getColumnIndex("User_type"))));
        return UserType;
    }


    // Getting data from categories table

    public ArrayList<Category> GetCat() {
        ArrayList<Category> list = new ArrayList<Category>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from categories";


        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int id = cur.getInt((cur.getColumnIndex("cat_id")));
            String image = cur.getString((cur.getColumnIndex("cat_image")));
            String name = cur.getString((cur.getColumnIndex("cat_name")));
            Category cat = new Category(id,image,name);
            list.add(cat);
            cur.moveToNext();
        }
        cur.close();

        return list;
    }

    // Delete Category

    public void DeleteCatById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "delete from categories where cat_id="+id;

        db.execSQL(sql);
        db.close();
    }

    //Delete Item

    public void DeleteItemById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete from items where item_id="+id;
        db.execSQL(sql);
        db.close();
    }
    public void DeleteOrderById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "delete  from  orders where orders.order_id="+id;
        db.execSQL(sql);
        db.close();
    }




    // Edit Category

    public void editcat(String name,int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql ="UPDATE categories  SET cat_name='"+name+"' WHERE categories.cat_id="+id;
        db.execSQL(sql);
        db.close();

    }

    // Edit Item

    public void edititem(String name,String quantity,String price,int CatId, String itemdetails,int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="UPDATE items  SET item_name='"+name+"',item_price='"+price+"',qty='"+quantity+"',cat_item_id='"+CatId+"',item_details='"+itemdetails+"' WHERE items.item_id="+id;
        db.execSQL(sql);
        db.close();
    }

    // Update Stock when customer make order

    public void UpdateStock(int soldqty , int itemid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="update items SET qty=qty-'"+soldqty+"'WHERE items.item_id="+itemid;
        db.execSQL(sql);
        db.close();
    }



    // Add Category

    public void AddnewCat(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="INSERT INTO categories (cat_name) VALUES ('"+name+"')";
        db.execSQL(sql);
        db.close();
    }

    // Add Item

    public void AddnewItem(String name,String quantity,String price,int CatId, String itemdetails)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="INSERT INTO items (item_name,item_price,qty,cat_item_id,item_details) VALUES ('"+name+"','"+price+"','"+quantity+"','"+CatId+"','"+itemdetails+"')";
        db.execSQL(sql);
        db.close();
    }

    // Save customer order in table orders

    public void InsertOrders(int orderid,int userid,int productid,int quantity) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="INSERT INTO orders (order_id,order_user_id,order_product_id,quantity) VALUES ("+orderid+","+userid+","+productid+","+quantity+")";
        db.execSQL(sql);
        db.close();
    }

    // Get data of items

    public ArrayList<item> GetItemById(int Catid,int status) {
        ArrayList<item> list = new ArrayList<item>();
        String sql;
        SQLiteDatabase db = this.getReadableDatabase();
         if(status==2) { sql = "select * from items where cat_item_id=" + Catid + " AND items.qty>'0'";

             }
         else
         {
           sql = "select * from items where cat_item_id="+Catid;

           }
        Cursor cur = db.rawQuery(sql, null);


        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int id = cur.getInt((cur.getColumnIndex("item_id")));
            String image = cur.getString((cur.getColumnIndex("item_image")));
            String name = cur.getString((cur.getColumnIndex("item_name")));
            String price = cur.getString((cur.getColumnIndex("item_price")));
            String details = cur.getString((cur.getColumnIndex("item_details")));
            int quantity = cur.getInt((cur.getColumnIndex("qty")));
            item it = new item(id,image, name,price,quantity,details);

            list.add(it);
            cur.moveToNext();
        }
        cur.close();

        return list;
    }

    public ArrayList<item> GetAllItems (int status) {
        ArrayList<item> list = new ArrayList<item>();
        String sql;
        SQLiteDatabase db = this.getReadableDatabase();
  if(status==2) {
       sql = "select * from items where items.qty>'1'";
  }
  else
  {
       sql = "select * from items";
  }


        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int id = cur.getInt((cur.getColumnIndex("item_id")));
            String image = cur.getString((cur.getColumnIndex("item_image")));
            String name = cur.getString((cur.getColumnIndex("item_name")));
            String price = cur.getString((cur.getColumnIndex("item_price")));
            String details = cur.getString((cur.getColumnIndex("item_details")));
            int quantity = cur.getInt((cur.getColumnIndex("qty")));
            item it = new item(id,image, name,price,quantity,details);

            list.add(it);
            cur.moveToNext();
        }
        cur.close();

        return list;
    }


    // get last order id to increment it when customer make order

    public int GetLastOrder_id ()
    {
        int id =0 ;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = " SELECT * FROM orders Order BY table_id DESC LIMIT 1";

        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
             id = cur.getInt((cur.getColumnIndex("order_id")));
            cur.moveToNext();
        }
        cur.close();
        return id;
    }
    // Get The quantity of items in stock
    public int stockquantity(int item_id)
    {
        int stock_qty =0 ;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = " SELECT qty FROM items where items.item_id="+item_id;

        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            stock_qty= cur.getInt((cur.getColumnIndex("qty")));
            cur.moveToNext();
        }
        cur.close();
        return stock_qty;
    }

    // Show Customer their previous orders

    public ArrayList<vieworderrow> CustomerViewOrder(int user_id) {
        ArrayList<vieworderrow> list = new ArrayList<vieworderrow>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT orders.order_id , users.name ,count(items.item_id) as 'Number of items',sum(items.item_price*orders.quantity) as 'Total price' from users INNER JOIN orders INNER Join items WHERE users.id=orders.order_user_id AND orders.order_product_id=items.item_id GROUP BY orders.order_id HAVING  users.id="+user_id;


        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int order_id = cur.getInt((cur.getColumnIndex("order_id")));
            String name = cur.getString((cur.getColumnIndex("name")));
            String number_items=cur.getString((cur.getColumnIndex("Number of items")));
            String totalprice = cur.getString((cur.getColumnIndex("Total price")));

            vieworderrow vo = new vieworderrow(name,number_items,totalprice,order_id);


            list.add(vo);
            cur.moveToNext();
        }
        cur.close();

        return list;
    }


    // Show Admin all orders maked

    public ArrayList<vieworderrow> AdminViewAllOrders() {
        ArrayList<vieworderrow> list = new ArrayList<vieworderrow>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT orders.order_id , users.name ,count(items.item_id) as 'Number of items',sum(items.item_price*orders.quantity) as 'Total price' from users INNER JOIN orders INNER Join items WHERE users.id=orders.order_user_id AND orders.order_product_id=items.item_id GROUP BY orders.order_id";


        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int order_id = cur.getInt((cur.getColumnIndex("order_id")));
            String name = cur.getString((cur.getColumnIndex("name")));
            String number_items=cur.getString((cur.getColumnIndex("Number of items")));
            String totalprice = cur.getString((cur.getColumnIndex("Total price")));

            vieworderrow vo = new vieworderrow(name,number_items,totalprice,order_id);


            list.add(vo);
            cur.moveToNext();
        }
        cur.close();

        return list;
    }


    public ArrayList<vieworderrow> AdminViewAllSoldItems()
    {
        ArrayList<vieworderrow> list = new ArrayList<vieworderrow>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT orders.order_id, item_name ,sum(orders.quantity) AS Quantity ,sum(items.item_price*orders.quantity) as 'Total Price' FROM orders INNER join items WHERE orders.order_product_id=items.item_id GROUP BY item_name  Order by Quantity DESC limit 3";

        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int order_id = cur.getInt((cur.getColumnIndex("order_id")));
            String name = cur.getString((cur.getColumnIndex("item_name")));
            String number_items=cur.getString((cur.getColumnIndex("Quantity")));
            String totalprice = cur.getString((cur.getColumnIndex("Total Price")));

            vieworderrow vo = new vieworderrow(name,number_items,totalprice,order_id);

            list.add(vo);
            cur.moveToNext();
        }
        cur.close();

        return list;
        }

    //Register
    public void AddNewAccount(String name,int User_type,String pwd,String gender)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="INSERT INTO users (name,password,User_type,Gender) VALUES ('"+name+"','"+pwd+"','"+User_type+"','"+gender+"')";
        db.execSQL(sql);
        db.close();
    }

   // Transactions
    public void NewTransaction(int user_id,String date,int action,String aplied_name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="INSERT INTO Transacions (tran_user_id,tran_date,tran_action,aplied_tran_name) VALUES ('"+user_id+"','"+date+"','"+action+"','"+aplied_name+"')";
        db.execSQL(sql);
        db.close();
    }
    public void Login_Register(String user_name,String date,int status)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="INSERT INTO Login_Register (history_user_name,history_date,action) VALUES ('"+user_name+"','"+date+"','"+status+"')";
        db.execSQL(sql);
        db.close();
    }
    public void DeleteLoginHistory(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="DELETE from Login_Register where Login_Register.id="+id;
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<transaction> ViewLogin_Register()
    {
        ArrayList<transaction> list = new ArrayList<transaction>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * from Login_Register";

        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int history_id =cur.getInt((cur.getColumnIndex("id")));
            String name = cur.getString((cur.getColumnIndex("history_user_name")));
            String date=cur.getString((cur.getColumnIndex("history_date")));
            int action = cur.getInt((cur.getColumnIndex("action")));

            transaction tran = new transaction(name,action,date,history_id);

            list.add(tran);
            cur.moveToNext();
        }
        cur.close();

        return list;
    }

    public ArrayList<CRUDTran> ViewOtherTran()
    {
        ArrayList<CRUDTran> list = new ArrayList<CRUDTran>();

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select Transacions.tran_id, users.name ,Transacions.tran_action,Transacions.aplied_tran_name,tran_date from Transacions INNER JOIN users WHERE users.id=Transacions.tran_user_id ORDER by tran_id";

        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            int id =cur.getInt((cur.getColumnIndex("tran_id")));
            String uname = cur.getString((cur.getColumnIndex("name")));
            String date=cur.getString((cur.getColumnIndex("tran_date")));
            int action = cur.getInt((cur.getColumnIndex("tran_action")));
            String name = cur.getString((cur.getColumnIndex("aplied_tran_name")));

            CRUDTran tran = new CRUDTran(uname,action,date,name,id);

            list.add(tran);
            cur.moveToNext();
        }
        cur.close();

        return list;
    }
    public void DeleteCrud(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="DELETE from Transacions where tran_id="+id;
        db.execSQL(sql);
        db.close();
    }

    public String GetProfit()
    {
        String profit = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT sum(items.item_price*orders.quantity) as 'Profit' FROM orders INNER join items WHERE orders.order_product_id=items.item_id ";

        Cursor cur = db.rawQuery(sql, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            profit = cur.getString((cur.getColumnIndex("Profit")));
            cur.moveToNext();
        }
        cur.close();
        return profit;
    }



}
