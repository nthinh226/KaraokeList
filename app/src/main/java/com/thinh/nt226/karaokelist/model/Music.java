package com.thinh.nt226.karaokelist.model;

public class Music{
    private String ma;
    private String ten;
    private String caSy;
    private int thich;


    public Music(String ma, String ten, String caSy, int thich) {
        this.ma = ma;
        this.ten = ten;
        this.caSy = caSy;
        this.thich = thich;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCaSy() {
        return caSy;
    }

    public void setCaSy(String caSy) {
        this.caSy = caSy;
    }

    public int getThich() {
        return thich;
    }

    public void setThich(int thich) {
        this.thich = thich;
    }
}
