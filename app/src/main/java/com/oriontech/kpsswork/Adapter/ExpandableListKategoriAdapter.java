package com.oriontech.kpsswork.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.oriontech.kpsswork.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 29182967598 on 12.02.2018.
 */

public class ExpandableListKategoriAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<String> listParent;
    private HashMap<String,List<String>> listChild;


    public ExpandableListKategoriAdapter(Context context, List<String> listParent, HashMap<String, List<String>> listChild) {
        this.context = context;
        this.listParent = listParent;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listParent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listParent.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listParent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listParent.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String kategori = getGroup(groupPosition).toString();
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_dersler, null);
        }
        TextView tvKategori = convertView.findViewById(R.id.tvListItemKategori);
        tvKategori.setTypeface(null, Typeface.BOLD);
        tvKategori.setText(kategori);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // kaçıncı pozisyonda ise başlığımızın elemanı onun ismini alarak string e atıyoruz
        String kategoriChild = getChild(groupPosition,childPosition).toString();
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_dersler_child,null);
        }
        // listview_child ulaştığımıza göre içindeki bileşeni de kullanabiliyoruz daha sonradan view olarak return ediyoruz
        TextView tvDeneme=convertView.findViewById(R.id.tvDeneme_KategoriChild);
        tvDeneme.setText(kategoriChild);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
