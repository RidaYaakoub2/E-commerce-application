package com.example.finalproject;

public class transaction {

   private  String name;
   private int actionmade;
   private String date;
   private int history_id;
    protected transaction(String name,int ActionMade,String date,int history_id)

    {
     this.setName(name);
     this.setActionmade(ActionMade);
     this.setDate(date);
     this.setHistory_id(history_id);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getActionmade() {
        return actionmade;
    }

    public void setActionmade(int actionmade) {
        this.actionmade = actionmade;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }
}
