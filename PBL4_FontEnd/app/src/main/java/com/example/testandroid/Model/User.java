package com.example.testandroid.Model;

import java.util.List;

public class User {
    private Integer mssv;
    private String name;
    private String email;
    private String password;
    private String lop;
    private String khoa;

    public User(Integer mssv, String name, String email, String password, String lop, String khoa) {
        this.mssv = mssv;
        this.name = name;
        this.email = email;
        this.password = password;
        this.lop = lop;
        this.khoa = khoa;
    }
    public User( String name, String email, String password, String lop, String khoa) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.lop = lop;
        this.khoa = khoa;
    }
    public Integer getMssv() { return  mssv;}
    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setMssv(String email) {
        this.mssv = mssv;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}