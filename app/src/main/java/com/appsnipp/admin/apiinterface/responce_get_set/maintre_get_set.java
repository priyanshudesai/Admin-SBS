package com.appsnipp.admin.apiinterface.responce_get_set;

public class maintre_get_set {
    int id;
    String billname,flatno;

    public maintre_get_set(int id, String billname, String flatno) {
        this.id = id;
        this.billname = billname;
        this.flatno = flatno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }
}
