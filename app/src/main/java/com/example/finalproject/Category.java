package com.example.finalproject;

public class Category {

    private int id;
    private String catimage;
    private String catname;

  protected Category(int id,String Catimage, String catname)
  {
      this.setId(id);
      this.setCatimage(Catimage);
     this.setCatname(catname);
  }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatimage() {
        return catimage;
    }

    public void setCatimage(String catimage) {
        this.catimage = catimage;
    }
}
