package com.example.finalproject;

public class vieworderrow {

    private String name;
    private String num_items;
    private String totalprice;
    private int order_id;

    protected vieworderrow (String name ,String num_items,String totalprice,int order_id )
    {
        this.name=name;
        this.num_items=num_items;
        this.totalprice=totalprice;
        this.setOrder_id(order_id);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum_items() {
        return num_items;
    }

    public void setNum_items(String num_items) {
        this.num_items = num_items;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
