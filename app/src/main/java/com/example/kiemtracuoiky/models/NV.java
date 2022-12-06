package com.example.kiemtracuoiky.models;

public class NV {
    int id;
    int pbid;
    String name;
    String sdt;
    String mail;

    public NV(int id, int pbid, String name, String sdt, String mail) {
        this.id = id;
        this.pbid = pbid;
        this.name = name;
        this.sdt = sdt;
        this.mail = mail;
    }

    public int getPbid() {
        return pbid;
    }

    public void setPbid(int pbid) {
        this.pbid = pbid;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
