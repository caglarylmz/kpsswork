package com.oriontech.kpsswork.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.oriontech.kpsswork.Adapter.ExpandableListKategoriAdapter;
import com.oriontech.kpsswork.DB.DBHelper;
import com.oriontech.kpsswork.Interfaces.Backable;
import com.oriontech.kpsswork.Models.Dersler;
import com.oriontech.kpsswork.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 29182967598 on 09.02.2018.
 */

public class FHomeDersler extends Fragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupExpandListener,Backable{
    ExpandableListView expandListView;
    ExpandableListKategoriAdapter mAdapter;
    Context context;
    HashMap<String,List<String>> listChild;
    List<String> listHeader;
    DBHelper dbHelper;
    List<Dersler> dersler;
    int lastPosition=-1;

    //Fragment yaratılıyor
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context=container.getContext();
        return inflater.inflate(R.layout.f_home_dersler,container,false);

    }
    //Fragment yaratıldığında olacaklar
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expandListView = getActivity().findViewById(R.id.expand_listview);
        dbHelper=new DBHelper(context);
        initData();
        mAdapter = new ExpandableListKategoriAdapter(context,listHeader,listChild);
        expandListView.setAdapter(mAdapter);
        //click özelliği verilmeli
        expandListView.setClickable(true);
        //child a tıklanınca olacaklar
        expandListView.setOnChildClickListener(this);
        //parent'e menu açılınca olacaklar
        expandListView.setOnGroupExpandListener(this);

    }
    //load parent&child data
    private void initData() {
        dersler = new ArrayList<>();
        //databaseden Katagori table'dan çekilen veriler
        dersler=dbHelper.getDerslerList();
        //Sadece ders adını alıyoruz
        listHeader = new ArrayList<>();
        for(int i=0;i<dersler.size();i++){
            listHeader.add(dersler.get(i).getDersAdi());
        }

        listChild=new HashMap<>();
        List<String> child = new ArrayList<>();
        child.add("DENEME SINAVLARI");
        child.add("KONU TESTLERİ");

        for (int i = 0 ; i<listHeader.size();i++) {
            listChild.put(listHeader.get(i), child);
        }

    }
    //child click listener
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        switch (groupPosition){
            case 0:
                switch (childPosition){
                    case 0:
                        Toast.makeText(context, "Türkçe Deneme", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Bundle args = new Bundle();
                        args.putInt("dersID",groupPosition+1);
                        fhDerslerKonular.setArguments(args);
                        showFragment(fhDerslerKonular,"konular");
                        Toast.makeText(context, "Türkçe Konu", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case 1:
                switch (childPosition){
                    case 0:
                        Toast.makeText(context, "Matematik Deneme", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Bundle args = new Bundle();
                        args.putInt("dersID",groupPosition+1);
                        fhDerslerKonular.setArguments(args);
                        showFragment(fhDerslerKonular,"konular");
                        Toast.makeText(context, "Matematik Konu", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case 2:
                switch (childPosition){
                    case 0:
                        Toast.makeText(context, "Tarih Deneme", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Bundle args = new Bundle();
                        args.putInt("dersID",groupPosition+1);
                        fhDerslerKonular.setArguments(args);
                        showFragment(fhDerslerKonular,"konular");
                        Toast.makeText(context, "Tarih Konu", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case 3:
                switch (childPosition){
                    case 0:
                        Toast.makeText(context, "Coğrafya Deneme", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Bundle args = new Bundle();
                        args.putInt("dersID",groupPosition+1);
                        fhDerslerKonular.setArguments(args);
                        showFragment(fhDerslerKonular,"konular");
                        Toast.makeText(context, "Coğrafya Konu", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case 4:
                switch (childPosition){
                    case 0:
                        Toast.makeText(context, "Vatandaşlık Deneme", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Bundle args = new Bundle();
                        args.putInt("dersID",groupPosition+1);
                        fhDerslerKonular.setArguments(args);
                        showFragment(fhDerslerKonular,"konular");
                        Toast.makeText(context, "Vatandaşlık Konu", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
        return true;
    }
    //parent expand listener
    @Override
    public void onGroupExpand(int groupPosition) {
            if(lastPosition!=-1 && lastPosition!= groupPosition){
                expandListView.collapseGroup(lastPosition);
            }
            lastPosition=groupPosition;
        }

    public void showFragment(Fragment fragment, String tag){
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.frame_contentMain,fragment,tag);
        fTransaction.addToBackStack(tag);
        fTransaction.commit();
    }



}
