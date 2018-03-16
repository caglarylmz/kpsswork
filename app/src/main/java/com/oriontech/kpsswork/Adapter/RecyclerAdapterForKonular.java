package com.oriontech.kpsswork.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oriontech.kpsswork.DB.DBHelper;
import com.oriontech.kpsswork.Models.Konular;
import com.oriontech.kpsswork.R;

import java.util.List;

/**
 * extends RecyclerView.ViewHolder for viewHolder
 * extends RecyclerView.Adapter<RecylerAdapterForKonular.KonularViewHolder> for RecyclerAdapterForKonular
 */

public class RecyclerAdapterForKonular extends RecyclerView.Adapter<RecyclerAdapterForKonular.KonularViewHolder> {
    private Context context;
    private List<Konular> konularList;
    private MyClickListener listener;
    private DBHelper db;

    public RecyclerAdapterForKonular(Context context, List<Konular> konularList) {
        this.context = context;
        this.konularList = konularList;
    }

    //extend ettiğimiz ViewHolder'ı  döner
    @Override
    public KonularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_list_konular,null);
        KonularViewHolder holder = new KonularViewHolder(view,listener);
        db=new DBHelper(context);
        return holder;

    }
    //ViewHolder'a elementleri getirir.
    @Override
    public void onBindViewHolder(KonularViewHolder holder, int position) {
        Konular konu = konularList.get(position);

        holder.tvKonuId.setText(String.valueOf(position+1));
        holder.tvKonuAdi.setText(konularList.get(position).getKonuAdi());
        holder.tvDogruSayisi.setText(String.valueOf(db.getKonuDogruSayisi(konu.getID())));
        holder.tvYanlisSayisi.setText(String.valueOf(db.getKonuYanlisSayisi(konu.getID())));
        holder.tvSoruSayisi.setText(String.valueOf(db.getKonuSoruSayisi(konu.getID())));
        if(konu.getFavoriMi()==1)
            holder.ivFavori.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            holder.ivFavori.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }
    //listenin size'
    @Override
    public int getItemCount() {
        return konularList.size();
    }


    public void setOnItemClickListener(MyClickListener listener){
        this.listener=listener;
    }

    class KonularViewHolder extends RecyclerView.ViewHolder{
        //list Item'da bulunan elementleri burada ekiliyoruz
        TextView tvKonuId;
        TextView tvKonuAdi;
        TextView tvDogruSayisi;
        TextView tvYanlisSayisi;
        TextView tvSoruSayisi;
        ImageView ivFavori;


        public KonularViewHolder(View itemView, final MyClickListener listener) {
            super(itemView);
            //xml'deki elementleri bu kısımda tanıtıyoruz
            tvKonuId=itemView.findViewById(R.id.konuID_itemKonular);
            tvKonuAdi=itemView.findViewById(R.id.konuAdi_itemKonular);
            tvSoruSayisi=itemView.findViewById(R.id.SoruSayisi_itemKonular);
            tvDogruSayisi=itemView.findViewById(R.id.DogruSayisi_itemKonular);
            tvYanlisSayisi=itemView.findViewById(R.id.YanlisSayisi_itemKonular);
            ivFavori=itemView.findViewById(R.id.ivListitem_Favorites__itemKonular);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(listener!=null){
                      int position = getAdapterPosition();
                      if(position!=RecyclerView.NO_POSITION){
                          listener.onItemClick(position);
                      }
                  }
                }
            });
            //imageview'e tıklanınca olacaklar
            ivFavori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onImageViewClick(position);
                        }
                    }
                }
            });
        }
    }
    public interface MyClickListener{
        void onItemClick(int position);
        void onImageViewClick(int position);
    }
}
