package com.example.finalproject;

public class item {
    private int id;
    private String image;
    private String name;
    private String price;
    private int quantity;
    private String details;
 protected item(int id,String image,String name,String price,int quantity,String details)
 {
     this.setId(id);
     this.setImage(image);
     this.setName(name);
     this.setPrice(price);
     this.setQuantity(quantity);
     this.setDetails(details);
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



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
