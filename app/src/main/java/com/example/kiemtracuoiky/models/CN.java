package com.example.kiemtracuoiky.models;

public class CN {
    int id;

    public int getTpid() {
        return tpid;
    }

    public void setTpid(int tpid) {
        this.tpid = tpid;
    }

    int tpid;
    String name;

    public CN(int id, String name,int tpid) {
        this.id = id;
        this.name = name;
        this.tpid = tpid;
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
