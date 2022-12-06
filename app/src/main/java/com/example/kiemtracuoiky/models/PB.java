package com.example.kiemtracuoiky.models;

public class PB {
    int id;

    public PB(int id, int cnid, String name) {
        this.id = id;
        this.cnid = cnid;
        this.name = name;
    }


    public int getCnid() {
        return cnid;
    }

    public void setCnid(int cnid) {
        this.cnid = cnid;
    }

    int cnid;
    String name;

    public PB(int id, String name,int cnid) {
        this.id = id;
        this.name = name;
        this.cnid = cnid;
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
}
