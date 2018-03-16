package com.oriontech.kpsswork.Models;

/**
 * Created by 29182967598 on 14.02.2018.
 */

public class Konular {
    int ID;
    int dersID;
    String konuAdi;
    int favoriMi;
    int soruSayisi;
    int dogruSayisi;
    int yanlisSayisi;

    public Konular() {
    }

    public Konular(int konuID, int dersID, String konuAdi, int favoriMi) {
        this.ID = konuID;
        this.dersID = dersID;
        this.konuAdi = konuAdi;
        this.favoriMi = favoriMi;
    }

    public Konular(int ID, int soruSayisi, int dogruSayisi, int yanlisSayisi) {
        this.ID = ID;
        this.soruSayisi = soruSayisi;
        this.dogruSayisi = dogruSayisi;
        this.yanlisSayisi = yanlisSayisi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDersID() {
        return dersID;
    }

    public void setDersID(int dersID) {
        this.dersID = dersID;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public int getFavoriMi() {
        return favoriMi;
    }

    public void setFavoriMi(int favori) {
        favoriMi = favori;
    }

    public int getSoruSayisi() {
        return soruSayisi;
    }

    public void setSoruSayisi(int soruSayisi) {
        this.soruSayisi = soruSayisi;
    }

    public int getDogruSayisi() {
        return dogruSayisi;
    }

    public void setDogruSayisi(int dogruSayisi) {
        this.dogruSayisi = dogruSayisi;
    }

    public int getYanlisSayisi() {
        return yanlisSayisi;
    }

    public void setYanlisSayisi(int yanlisSayisi) {
        this.yanlisSayisi = yanlisSayisi;
    }
}
