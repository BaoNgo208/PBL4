package com.example.testandroid.Model;

public class BoMon {
    private String IdBM;
    private String TenBM;
    private String Khoa;

    public BoMon(String idBM, String tenBM, String khoa) {
        IdBM = idBM;
        TenBM = tenBM;
        Khoa = khoa;
    }

    public String getIdBM() {
        return IdBM;
    }

    public void setIdBM(String idBM) {
        IdBM = idBM;
    }

    public String getTenBM() {
        return TenBM;
    }

    public void setTenBM(String tenBM) {
        TenBM = tenBM;
    }

    public String getKhoa() {
        return Khoa;
    }

    public void setKhoa(String khoa) {
        Khoa = khoa;
    }
}
