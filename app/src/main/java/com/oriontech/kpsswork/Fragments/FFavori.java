package com.oriontech.kpsswork.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oriontech.kpsswork.Adapter.RecyclerAdapterForFavorites;
import com.oriontech.kpsswork.Adapter.RecyclerAdapterForKonular;
import com.oriontech.kpsswork.DB.DBHelper;
import com.oriontech.kpsswork.Interfaces.Backable;
import com.oriontech.kpsswork.Models.Konular;
import com.oriontech.kpsswork.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 29182967598 on 14.03.2018.
 */

public class FFavori extends Fragment implements Backable{
    Context context;
    RecyclerView recyclerView;
    DBHelper dbHelper;
    List<Konular> konular;
    RecyclerAdapterForFavorites mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.context=container.getContext();
        return inflater.inflate(R.layout.f_favorites,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initData();
    }

    private void init(){
        recyclerView = getActivity().findViewById(R.id.recylerviewFavorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
    private void initData() {
        dbHelper=new DBHelper(context);
        konular=new ArrayList<>();
        konular=dbHelper.getKonularFavorilerList();
        mAdapter=new RecyclerAdapterForFavorites(context,konular);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerAdapterForFavorites.MyClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle args = new Bundle();
                args.putInt("konuID",konular.get(position).getID());
                args.putInt("dersID",konular.get(position).getDersID());
                fhdkTestler.setArguments(args);
                showFragment(fhdkTestler,"testler");
                Toast.makeText(context, konular.get(position).getKonuAdi(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onImageViewClick(int position) {
                if(konular.get(position).getFavoriMi()==1){
                    konular.get(position).setFavoriMi(0);
                    mAdapter.notifyItemChanged(position);
                    dbHelper.setKonularFavori(konular.get(position));
                }else {
                    konular.get(position).setFavoriMi(1);
                    mAdapter.notifyItemChanged(position);
                    dbHelper.setKonularFavori(konular.get(position));
                }
            }
        });
    }
    private void showFragment(Fragment fragment,String tag){
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.frame_contentMain,fragment,tag);
        fTransaction.addToBackStack(tag);
        fTransaction.commit();
    }
}
