package com.oriontech.kpsswork.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oriontech.kpsswork.R;

/**
 * Created by 29182967598 on 08.02.2018.
 */

public class FIstatistik extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_istatistik,container,false);
        return view;
    }
}
