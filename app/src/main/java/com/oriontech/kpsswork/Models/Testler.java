package com.oriontech.kpsswork.Models;

/**
 * Created by 29182967598 on 19.02.2018.
 */

public class Testler {
    int ID;
    int dersID;
    int konuID;
    String testAdi;
    public Testler() {
    }

    public Testler(int ID, int dersId,int konuID, String testAdi) {
        this.ID = ID;
        this.dersID =dersId;
        this.konuID = konuID;
        this.testAdi = testAdi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getKonuID() {
        return konuID;
    }

    public void setKonuID(int konuID) {
        this.konuID = konuID;
    }

    public String getTestAdi() {
        return testAdi;
    }

    public void setTestAdi(String testAdi) {
        this.testAdi = testAdi;
    }

    public int getDersID() {
        return dersID;
    }

    public void setDersID(int dersID) {
        this.dersID = dersID;
    }
}
