package com.oriontech.kpsswork.Models;

/**
 * Created by 29182967598 on 12.02.2018.
 */

public class Dersler {
    private int ID;
    private String dersAdi;

    public Dersler(int dersID, String dersAdi) {
        this.ID = dersID;
        this.dersAdi = dersAdi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }
}
