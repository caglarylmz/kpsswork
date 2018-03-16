package com.oriontech.kpsswork.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriontech.kpsswork.DB.DBHelper;
import com.oriontech.kpsswork.Models.Konular;
import com.oriontech.kpsswork.Models.Testler;
import com.oriontech.kpsswork.R;

import java.util.List;

/**
 * Created by 29182967598 on 12.02.2018.
 */

public class ListTestlerAdapter extends BaseAdapter{

    private Context context;
    private List<Testler> testlerList;

    public ListTestlerAdapter(Context context, List<Testler> testlerList) {
        this.context = context;
        this.testlerList = testlerList;
    }

    @Override
    public int getCount() {
        return testlerList.size();
    }

    @Override
    public Object getItem(int position) {
        return testlerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return testlerList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_testler, null);
        }

        TextView tvTestAdi = convertView.findViewById(R.id.tvListItemTestAdi);
        tvTestAdi.setText(testlerList.get(position).getTestAdi());

        return convertView;
    }



}
