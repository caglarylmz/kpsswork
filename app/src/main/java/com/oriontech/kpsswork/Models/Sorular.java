package com.oriontech.kpsswork.Models;

/**
 * Created by 29182967598 on 19.02.2018.
 */

public class Sorular {
    int ID;
    int dersID;
    int konuID;
    int testID;
    String soruMetni;
    String soru;
    String a,b,c,d,e;
    String dogruCevap;
    int sonCevap;
    int dogruSayisi;
    int yanlisSayisi;
    int cozuldumu;

    public Sorular(int ID, int dersID, int konuID, int testID, String soruMetni, String soru , String a, String b, String c, String d, String e, String dogruCevap, int sonCevap, int dogruSayisi, int yanlisSayisi, int cozuldumu) {
        this.ID = ID;
        this.dersID=dersID;
        this.konuID=konuID;
        this.testID = testID;
        this.soruMetni = soruMetni;
        this.soru = soru;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.dogruCevap = dogruCevap;
        this.sonCevap = sonCevap;
        this.dogruSayisi = dogruSayisi;
        this.yanlisSayisi = yanlisSayisi;
        this.cozuldumu = cozuldumu;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getSoruMetni() {
        return soruMetni;
    }

    public void setSoruMetni(String soruMetni) {
        this.soruMetni = soruMetni;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getDogruCevap() {
        return dogruCevap;
    }

    public void setDogruCevap(String dogruCevap) {
        this.dogruCevap = dogruCevap;
    }

    public int getSonCevap() {
        return sonCevap;
    }

    public void setSonCevap(int sonCevap) {
        this.sonCevap = sonCevap;
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

    public int getCozuldumu() {
        return cozuldumu;
    }

    public void setCozuldumu(int cozuldumu) {
        this.cozuldumu = cozuldumu;
    }

    public int getDersID() {
        return dersID;
    }

    public void setDersID(int dersID) {
        this.dersID = dersID;
    }

    public int getKonuID() {
        return konuID;
    }

    public void setKonuID(int konuID) {
        this.konuID = konuID;
    }
}
