package com.oriontech.kpsswork.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oriontech.kpsswork.Adapter.ListTestlerAdapter;
import com.oriontech.kpsswork.DB.DBHelper;
import com.oriontech.kpsswork.Interfaces.Backable;
import com.oriontech.kpsswork.Models.Testler;
import com.oriontech.kpsswork.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 29182967598 on 05.03.2018.
 */

public class FHDKTestler extends Fragment implements ListView.OnItemClickListener,Backable{
    Context context;
    List<Testler> testlerList;
    Testler test;
    ListView listViewTestler;
    ListTestlerAdapter adapter;
    DBHelper db;
    int konuID;
    int dersID;
    TextView tvTestAdi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        dersID=getArguments().getInt("dersID");
        konuID=getArguments().getInt("konuID");
        View view=inflater.inflate(R.layout.f_h_d_konular_testler,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTestAdi=getActivity().findViewById(R.id.tvListItemTestAdi);
        listViewTestler=getActivity().findViewById(R.id.listviewTestler);
        initData();
    }

    private void initData() {
        db = new DBHelper(context);
        testlerList = new ArrayList<>();
        test = new Testler();
        testlerList=db.getTestlerList(dersID,konuID);
        adapter=new ListTestlerAdapter(context,testlerList);
        listViewTestler.setAdapter(adapter);
        listViewTestler.setOnItemClickListener(this);

    }

    private void showFragment(Fragment fragment,String tag){
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.frame_contentMain,fragment,tag);
        fTransaction.addToBackStack(tag);
        fTransaction.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle args = new Bundle();
        args.putInt("dersID",testlerList.get(position).getDersID());
        Toast.makeText(context, testlerList.get(position).getDersID() +","+
                testlerList.get(position).getKonuID()+","+
                testlerList.get(position).getID(), Toast.LENGTH_LONG).show();
        args.putInt("konuID",testlerList.get(position).getKonuID());
        args.putInt("testID",testlerList.get(position).getID());
        fSoru.setArguments(args);
        showFragment(fSoru,"sorular");
        //Toast.makeText(context, testlerList.get(position).getTestAdi(), Toast.LENGTH_SHORT).show();
    }
}


