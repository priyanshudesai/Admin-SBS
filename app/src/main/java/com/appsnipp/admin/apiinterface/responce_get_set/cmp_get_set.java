package com.appsnipp.admin.apiinterface.responce_get_set;

public class cmp_get_set {
    int id;
    String ctitle,discription,document_file,flatno;

    public cmp_get_set(int id, String ctitle, String discription, String document_file, String flatno) {
        this.id = id;
        this.ctitle = ctitle;
        this.discription = discription;
        this.document_file = document_file;
        this.flatno = flatno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDocument_file() {
        return document_file;
    }

    public void setDocument_file(String document_file) {
        this.document_file = document_file;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }
}
