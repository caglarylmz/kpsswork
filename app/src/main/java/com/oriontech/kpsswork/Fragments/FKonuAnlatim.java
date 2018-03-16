package com.oriontech.kpsswork.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriontech.kpsswork.Interfaces.Backable;
import com.oriontech.kpsswork.R;


public class FKonuAnlatim extends Fragment implements Backable {
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_konu_anlatim,container,false);
        context=container.getContext();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
