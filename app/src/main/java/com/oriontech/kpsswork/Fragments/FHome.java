package com.oriontech.kpsswork.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oriontech.kpsswork.Interfaces.Backable;
import com.oriontech.kpsswork.R;

/**
 * Created by 29182967598 on 08.02.2018.
 */

public class FHome extends Fragment implements View.OnClickListener,Backable{
    LinearLayout sinavLine,favoriLine,randomLine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_home,container,false);

        return view;
    }
    //activity Yaratıldıktan sonra xml viewlere erişilebilir
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)  {
        super.onActivityCreated(savedInstanceState);
        sinavLine=getActivity().findViewById(R.id.layoutSınavlar_FHome);
        favoriLine=getActivity().findViewById(R.id.layoutFavori_FHome);
        randomLine=getActivity().findViewById(R.id.layoutRastgele_FHome);

        sinavLine.setOnClickListener(this);
        favoriLine.setOnClickListener(this);
        randomLine.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.layoutSınavlar_FHome:
                showFragment(fHomeDersler,"dersler");
                break;
            case R.id.layoutFavori_FHome:
                showFragment(fFavori,"favoriler");
                break;
            case R.id.layoutRastgele_FHome:
                showFragment(fIstatistik,"istatistik");
                break;
        }
    }
    public void showFragment(Fragment fragment, String tag){
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.frame_contentMain,fragment,tag);
        fTransaction.addToBackStack(tag);
        fTransaction.commit();
    }



}
