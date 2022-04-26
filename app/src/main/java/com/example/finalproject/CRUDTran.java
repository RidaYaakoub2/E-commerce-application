package com.example.finalproject;

public class CRUDTran {
    private  int crudid;
    private String uname;
    private int actionmade;
    private String date;
    private String aplied_name;
    protected CRUDTran(String uname, int ActionMade, String date, String aplied_name,int crudid)

    {
        this.setUname(uname);
        this.crudid=crudid;
        this.setAplied_name(aplied_name);
        this.setActionmade(ActionMade);
        this.setDate(date);
    }



    public int getActionmade() {
        return actionmade;
    }

    public void setActionmade(int actionmade) {
        this.actionmade = actionmade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAplied_name() {
        return aplied_name;
    }

    public void setAplied_name(String aplied_name) {
        this.aplied_name = aplied_name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getCrudid() {
        return crudid;
    }

    public void setCrudid(int crudid) {
        this.crudid = crudid;
    }
}
