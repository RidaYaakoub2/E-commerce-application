package com.example.finalproject;

public class BasketItem {
    private int id;
    private String image;
    private String name;
    private String price;
    private int qty;

    BasketItem (int id,String image,String name,String price, int qty)
    {
        this.id=id;
        this.setImage(image);
        this.name=name;
        this.price=price;
        this.qty=qty;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public void incrementqty(int qty)
    {
        qty++;
    }
    public void decrementqty(int qty)
    {
        qty--;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
