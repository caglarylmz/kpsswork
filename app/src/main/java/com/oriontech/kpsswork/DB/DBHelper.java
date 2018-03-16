package com.oriontech.kpsswork.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.oriontech.kpsswork.Models.Dersler;
import com.oriontech.kpsswork.Models.Konular;
import com.oriontech.kpsswork.Models.Sorular;
import com.oriontech.kpsswork.Models.Testler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 29182967598 on 12.02.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase mDatabase;
    private static final int DBVERSION=1;
    private static final String DBNAME= "kpss.db";
    private static String DBPATH="/data/data/com.oriontech.kpsswork/databases/";
    private static final String[] dbTABLES={"Dersler","Konular","Sorular","Testler"};
    private static final String[] dbDerslerCOL={"ID","dersAdi"};
    private static final String[] dbKonularCOL={"ID","dersID","konuAdi","Favorimi",};
    private static final String[] dbTestlerCOL={"ID","dersID","konuID","testAdi"};
    private static final String[] dbSorularCOL={"ID","dersID","konuID","testID","soruMetni","soru","a","b","c","d","e","dogruCevap","sonCevap","dogruSayisi","yanlisSayisi","Cozuldumu"};

    public DBHelper(Context context ) {
        //db databases altında oluşur
        super(context, DBNAME, null, DBVERSION);
        this.context=context;
        Log.i("DBPATH",DBPATH);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //uygulamada veri tabanını açmak için
    public void openDB() throws SQLiteException{
        String dbPath=DBPATH+DBNAME;
        if(mDatabase!=null && mDatabase.isOpen()){
            return;
        }
        mDatabase=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDB() {
        if (mDatabase!=null)
            mDatabase.close();
        super.close();
    }

    //dbKontrol ile db varsa işlem yapma, yoksa kopyala
    public void createDB() throws SQLiteException{
        if(!dbExists()){
            this.getReadableDatabase();
            try {
                copyDB();
                Log.i("hata","Veritabanı kopyalandı");
            }catch (Exception e){
                Log.i("hata","Veritabanı kopyalanamıyor");
                throw new Error("Veritabanı kopyalanamıyor");
            }
        }
    }
    //db Kontrol eder
    private boolean dbExists() {
        SQLiteDatabase checkDB = null;

        String path=DBPATH+DBNAME;
        try {
            checkDB=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){
            Log.w("hata","Veritabanı açılamadı");
        }
        //db oluşturulmuşsa true, oluşturulmamışsa false döner
        if(checkDB!=null)
            checkDB.close();
        return checkDB!=null?true:false;
    }
    //db kopyalar
    private void copyDB() {
        //assets-->databases
        try {
            InputStream myInput=context.getAssets().open(DBNAME);
            String outFileName= DBPATH+DBNAME;
            OutputStream myOutput=new FileOutputStream(outFileName);

            byte[] buffer=new byte[1024];
            int lenght;
            while ((lenght=myInput.read(buffer))>0){
                myOutput.write(buffer,0,lenght);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            Log.w("hata","Kopya oluşturma hatası");
        }
    }

    public Cursor queryData(String query){
        return mDatabase.rawQuery(query,null);

    }
    public SQLiteDatabase getDB(){
        return mDatabase;
    }

    public List<Dersler> getDerslerList(){
        List<Dersler> dersler = new ArrayList<>();
        Dersler katagori;
        openDB();
        String query= "SELECT * FROM " + dbTABLES[0];
        this.getReadableDatabase();
        Cursor cursor = queryData(query);
        while (cursor.moveToNext()) {
            katagori = new Dersler(cursor.getInt(0),cursor.getString(1));
            dersler.add(katagori);
            }
        cursor.close();
        closeDB();
        return dersler;
    }
    public List<Konular> getKonularList(int dersID){
        List<Konular> konular = new ArrayList<>();
        Konular konu;
        openDB();
        String query= "SELECT * FROM " + dbTABLES[1] + " where " + dbKonularCOL[1] + " = " + dersID;
        //this.getReadableDatabase();
        Cursor cursor = getDB().rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                konu = new Konular(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), (cursor.getInt(3)));
                konular.add(konu);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();
        return konular;
    }
    public List<Konular> getKonularFavorilerList(){
        List<Konular> konular = new ArrayList<>();
        Konular konu;
        openDB();
        String query= "SELECT * FROM " + dbTABLES[1] + " where " + dbKonularCOL[3] + " = 1";
        //this.getReadableDatabase();
        Cursor cursor = getDB().rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                konu = new Konular(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), (cursor.getInt(3)));
                konular.add(konu);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();
        return konular;
    }
    public void setKonularFavori(Konular konu){
        openDB();
        this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbKonularCOL[3],konu.getFavoriMi());

        mDatabase.update(dbTABLES[1],values,dbKonularCOL[0] +" = ?",new String[]{String.valueOf(String.valueOf(konu.getID()))});
        closeDB();
    }
    public List<Testler> getTestlerList(int dersID,int konuID){
        List<Testler> testlerList = new ArrayList<>();
        Testler test;
        openDB();
        String query= "SELECT * FROM " + dbTABLES[3] + " where " + dbTestlerCOL[1] + " = " + dersID + " AND " +  dbTestlerCOL[2] + " = " + konuID;
        Cursor cursor=getDB().rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                test=new Testler(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3));
                testlerList.add(test);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();
        return testlerList;
    }
    public List<Sorular> getSorularList(int dersID, int konuId, int testID){
        List<Sorular> sorularList=new ArrayList<>();
        Sorular soru;
        openDB();
        String query= "SELECT * FROM " + dbTABLES[2] + " where " + dbSorularCOL[1] + " = " + dersID
                + " AND " + dbSorularCOL[2] + " = "+ konuId + " AND " + dbSorularCOL[3] + " = " + testID ;
        Cursor cursor = getDB().rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                soru = new Sorular(cursor.getInt(0), cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),
                        cursor.getString(4), cursor.getString(5),cursor.getString(6), cursor.getString(7),
                        cursor.getString(8), cursor.getString(9),cursor.getString(10), cursor.getString(11),
                        cursor.getInt(12), cursor.getInt(13),cursor.getInt(14),cursor.getInt(15));
                sorularList.add(soru);
            } while (cursor.moveToNext());
        }
            cursor.close();
            closeDB();
            return sorularList;
        }

    public int getKonuSoruSayisi(int konuId){
        openDB();
        int soruSayisi=0;
        String query= "SELECT COUNT(*) FROM " + dbTABLES[2] + " where " + dbSorularCOL[2] + " = " + konuId;
        Cursor cursor = getDB().rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                soruSayisi=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();
        return soruSayisi;
    }
    public int getKonuDogruSayisi(int konuId){
        openDB();
        int soruSayisi=0;
        String query= "SELECT COUNT(*) FROM " + dbTABLES[2] + " where " + dbSorularCOL[2] + " = " + konuId + " AND sonCEVAP = 1";
        Cursor cursor = getDB().rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                soruSayisi=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();
        return soruSayisi;
    }
    public int getKonuYanlisSayisi(int konuId){
        openDB();
        int soruSayisi=0;
        String query= "SELECT COUNT(*) FROM " + dbTABLES[2] + " where " + dbSorularCOL[2] + " = " + konuId + " AND sonCEVAP = 0";
        Cursor cursor = getDB().rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                soruSayisi=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();
        return soruSayisi;
    }

    public void setSoruDogruYanlisSayisi(Sorular soru){
        openDB();
        this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbSorularCOL[13],soru.getDogruSayisi());
        values.put(dbSorularCOL[14],soru.getYanlisSayisi());
        values.put(dbSorularCOL[12],soru.getSonCevap());


        mDatabase.update(dbTABLES[2],values,dbSorularCOL[0] +" = ?",new String[]{String.valueOf(String.valueOf(soru.getID()))});
        closeDB();
    }
}
