package com.oriontech.kpsswork.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oriontech.kpsswork.DB.DBHelper;
import com.oriontech.kpsswork.Interfaces.Backable;
import com.oriontech.kpsswork.Models.Sorular;
import com.oriontech.kpsswork.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 29182967598 on 26.02.2018.
 */

public class FSoru extends Fragment implements View.OnClickListener,Backable{
    private List<Sorular> sorularList;
    private int[] cevaplarIDs;
    private DBHelper db;
    private Context context;
    private int dersID;
    private int konuID;
    private int testID;
    private int questionCounter=0;
    private int questioonCountTotal;
    private Sorular currentSoru;    private boolean answered;

    private ColorStateList defaultColorCardView;


    private BottomNavigationView topNavigationView;
    private BottomNavigationView bottomNavigationView;
    private WebView vwSoruMetni;
    private TextView tvSoru,tvChoiceA,tvChoiceB,tvChoiceC,tvChoiceD,tvChoiceE;
    private TextView tvSimdikiSoruId,tvToplamSoruSayisi,tvDogruSayisi,tvYanlisSayisi,tvTimer;
    private CardView cvChoiceA,cvChoiceB,cvChoiceC,cvChoiceD,cvChoiceE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.context=container.getContext();
        dersID=getArguments().getInt("dersID");
        konuID=getArguments().getInt("konuID");
        testID=getArguments().getInt("testID");
         View view= inflater.inflate(R.layout.f_soru,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initData();
        //Collections.shuffle(sorularList); //soruları karıştırır
        showQuestion();

    }

    private void init() {
        vwSoruMetni =getActivity().findViewById(R.id.tvsoruMetni);
        tvSoru=getActivity().findViewById(R.id.tvSoru);
        tvChoiceA=getActivity().findViewById(R.id.tvAnswerA);
        tvChoiceB=getActivity().findViewById(R.id.tvAnswerB);
        tvChoiceC=getActivity().findViewById(R.id.tvAnswerC);
        tvChoiceD=getActivity().findViewById(R.id.tvAnswerD);
        tvChoiceE=getActivity().findViewById(R.id.tvAnswerE);
        tvSimdikiSoruId=getActivity().findViewById(R.id.simdikiSoruId);
        tvToplamSoruSayisi=getActivity().findViewById(R.id.toplamSoruSayisi);
        tvDogruSayisi=getActivity().findViewById(R.id.dogruSayisi);
        tvYanlisSayisi=getActivity().findViewById(R.id.yanlisSayisi);
        topNavigationView=getActivity().findViewById(R.id.top_navigation);
        bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        cvChoiceA=getActivity().findViewById(R.id.cvAnswerA);
        cvChoiceB=getActivity().findViewById(R.id.cvAnswerB);
        cvChoiceC=getActivity().findViewById(R.id.cvAnswerC);
        cvChoiceD=getActivity().findViewById(R.id.cvAnswerD);
        cvChoiceE=getActivity().findViewById(R.id.cvAnswerE);
        tvChoiceA.setOnClickListener(this);
        tvChoiceB.setOnClickListener(this);
        tvChoiceC.setOnClickListener(this);
        tvChoiceD.setOnClickListener(this);
        tvChoiceE.setOnClickListener(this);

        defaultColorCardView=cvChoiceA.getCardBackgroundColor();

        ImageView ivRestart = getActivity().findViewById(R.id.ivTestiYenidenBaslat);
        ivRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCounter=0;
                for (int i =0;i<sorularList.size();i++){
                    cevaplarIDs[i]=0;
                    for (int j=0;j<sorularList.size();j++){

                    }
                }
                showQuestion();
            }
        });
        ImageView ivNotAl = getActivity().findViewById(R.id.ivNotAl);
        ivNotAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ivTestiYenidenBaslat:
                        questionCounter=0;
                        for (int i =0;i<sorularList.size();i++){
                            cevaplarIDs[i]=0;
                            for (int j=0;j<sorularList.size();j++){
                            }
                        }
                        showQuestion();
                        break;
                    case R.id.ivTestiBitir:
                        questionCounter=0;

                        break;
                    case R.id.ivNotAl:
                        break;
                }
                return true;
            }
        });*/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_back:
                        questionCounter--;
                        if(questionCounter>=0){
                            showQuestion();
                        }
                        else if(questionCounter< 0){
                            questionCounter=0;
                            Toast.makeText(context, "ilk soru", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.action_answer:

                        break;
                    case R.id.action_right:
                        questionCounter++;
                        if(questionCounter<questioonCountTotal){
                            showQuestion();
                       }
                        else if(questionCounter==questioonCountTotal){
                            questionCounter=questioonCountTotal-1;
                            Toast.makeText(context, "son soru", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });
    }
    private void initData() {
        tvDogruSayisi.setText(String.valueOf(0));
        tvYanlisSayisi.setText(String.valueOf(0));
        sorularList=new ArrayList<>();
        db = new DBHelper(context);
        sorularList=db.getSorularList(dersID,konuID,testID);
        questioonCountTotal=sorularList.size();
        //Toast.makeText(context, "soru sayısı = "+sorularList.size(), Toast.LENGTH_SHORT).show();
        if(sorularList.size()>0) {
            cevaplarIDs = new int[sorularList.size()];
            for (int i = 0; i < sorularList.size(); i++) {
                cevaplarIDs[i] = 0;
            }
        }else
            cevaplarIDs = new int[0];
    }

    private void showQuestion() {
       setDefaultBackgroundColor();
        if(sorularList.size()>0){
            //Toast.makeText(context, cevaplarIDs[questionCounter], Toast.LENGTH_SHORT).show();
            if (cevaplarIDs[questionCounter]==0){
                AnswersClickable();
                currentSoru = sorularList.get(questionCounter);
                //textview ile teststyle da sorun yaşadım webview kullandım.
                //tvSoruMetni.setText(Html.fromHtml(currentSoru.getSoruMetni())); ile textView'de html gösterilebilir. Fakat istediğim sonucu alamadım
                vwSoruMetni.loadData(currentSoru.getSoruMetni(), "text/html", "utf-8");
                tvSoru.setText(currentSoru.getSoru());
                tvChoiceA.setText(currentSoru.getA());
                tvChoiceB.setText(currentSoru.getB());
                tvChoiceC.setText(currentSoru.getC());
                tvChoiceD.setText(currentSoru.getD());
                tvChoiceE.setText(currentSoru.getE());
                tvSimdikiSoruId.setText(String.valueOf(questionCounter+1));
                tvToplamSoruSayisi.setText(String.valueOf(sorularList.size()));


            }else{
                AnswersNonClickable();
                TextView answer = getActivity().findViewById(cevaplarIDs[questionCounter]);
                currentSoru = sorularList.get(questionCounter);
                //textview ile teststyle da sorun yaşadım webview kullandım.
                // tvSoruMetni.setText(Html.fromHtml(currentSoru.getSoruMetni())); ile textView'de html gösterilebilir. Fakat istediğim sonucu alamadım
                vwSoruMetni.loadData(currentSoru.getSoruMetni(), "text/html", "utf-8");
                tvSoru.setText(currentSoru.getSoru());
                tvChoiceA.setText(currentSoru.getA());
                tvChoiceB.setText(currentSoru.getB());
                tvChoiceC.setText(currentSoru.getC());
                tvChoiceD.setText(currentSoru.getD());
                tvChoiceE.setText(currentSoru.getE());
                tvSimdikiSoruId.setText(String.valueOf(questionCounter+1));
                tvToplamSoruSayisi.setText(String.valueOf(sorularList.size()));
                if(currentSoru.getDogruCevap().equals(answer.getText().toString())){
                    answer.setTextColor(Color.GREEN);
                }else
                    answer.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(cvChoiceA.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceB.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceC.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceD.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceE.getCardBackgroundColor().equals(defaultColorCardView)&&v.getId()==R.id.tvAnswerA) {
            if (currentSoru.getDogruCevap().equals(tvChoiceA.getText().toString())) {
                cvChoiceA.setCardBackgroundColor(Color.GREEN);
                cevaplarIDs[questionCounter] = R.id.tvAnswerA;
                currentSoru.setDogruSayisi(currentSoru.getDogruSayisi()+1);
                currentSoru.setSonCevap(1);
                tvDogruSayisi.setText(String.valueOf(Integer.parseInt(tvDogruSayisi.getText().toString())+1));
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();

            } else {
                cvChoiceA.setCardBackgroundColor(Color.RED);
                cevaplarIDs[questionCounter] = R.id.tvAnswerA;
                tvYanlisSayisi.setText(String.valueOf(Integer.parseInt(tvYanlisSayisi.getText().toString())+1));
                currentSoru.setYanlisSayisi(currentSoru.getYanlisSayisi()+1);
                currentSoru.setSonCevap(0);
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            }
        }else if(cvChoiceA.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceB.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceC.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceD.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceE.getCardBackgroundColor().equals(defaultColorCardView)&&v.getId()==R.id.tvAnswerB) {
            if (currentSoru.getDogruCevap().equals(tvChoiceB.getText().toString())) {
                cvChoiceB.setCardBackgroundColor(Color.GREEN);
                cevaplarIDs[questionCounter] = R.id.tvAnswerB;
                currentSoru.setDogruSayisi(currentSoru.getDogruSayisi()+1);
                currentSoru.setSonCevap(1);
                tvDogruSayisi.setText(String.valueOf(Integer.parseInt(tvDogruSayisi.getText().toString())+1));
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            } else {
                cvChoiceB.setCardBackgroundColor(Color.RED);
                cevaplarIDs[questionCounter]=R.id.tvAnswerB;
                tvYanlisSayisi.setText(String.valueOf(Integer.parseInt(tvYanlisSayisi.getText().toString())+1));
                currentSoru.setYanlisSayisi(currentSoru.getYanlisSayisi()+1);
                currentSoru.setSonCevap(0);
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            }
        }else if(cvChoiceA.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceB.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceC.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceD.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceE.getCardBackgroundColor().equals(defaultColorCardView)&&v.getId()==R.id.tvAnswerC) {
            if (currentSoru.getDogruCevap().equals(tvChoiceC.getText().toString())) {
                cvChoiceC.setCardBackgroundColor(Color.GREEN);
                cevaplarIDs[questionCounter] = R.id.tvAnswerC;
                currentSoru.setDogruSayisi(currentSoru.getDogruSayisi()+1);
                currentSoru.setSonCevap(1);
                tvDogruSayisi.setText(String.valueOf(Integer.parseInt(tvDogruSayisi.getText().toString())+1));
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            } else {
                cvChoiceC.setCardBackgroundColor(Color.RED);
                cevaplarIDs[questionCounter] = R.id.tvAnswerC;
                tvYanlisSayisi.setText(String.valueOf(Integer.parseInt(tvYanlisSayisi.getText().toString())+1));
                currentSoru.setYanlisSayisi(currentSoru.getYanlisSayisi()+1);
                currentSoru.setSonCevap(0);
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            }
        }else if(cvChoiceA.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceB.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceC.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceD.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceE.getCardBackgroundColor().equals(defaultColorCardView)&&v.getId()==R.id.tvAnswerD) {
            if (currentSoru.getDogruCevap().equals(tvChoiceD.getText().toString())) {
                cvChoiceD.setCardBackgroundColor(Color.GREEN);
                cevaplarIDs[questionCounter] = R.id.tvAnswerD;
                currentSoru.setDogruSayisi(currentSoru.getDogruSayisi()+1);
                currentSoru.setSonCevap(1);
                tvDogruSayisi.setText(String.valueOf(Integer.parseInt(tvDogruSayisi.getText().toString())+1));
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            } else {
                cvChoiceD.setCardBackgroundColor(Color.RED);
                cevaplarIDs[questionCounter] = R.id.tvAnswerD;
                tvYanlisSayisi.setText(String.valueOf(Integer.parseInt(tvYanlisSayisi.getText().toString())+1));
                currentSoru.setYanlisSayisi(currentSoru.getYanlisSayisi()+1);
                currentSoru.setSonCevap(0);
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            }
        }else if(cvChoiceA.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceB.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceC.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceD.getCardBackgroundColor().equals(defaultColorCardView)&&
                cvChoiceE.getCardBackgroundColor().equals(defaultColorCardView)&&v.getId()==R.id.tvAnswerE){
            if(currentSoru.getDogruCevap().equals(tvChoiceE.getText().toString())){
                cvChoiceE.setCardBackgroundColor(Color.GREEN);
                cevaplarIDs[questionCounter]=R.id.tvAnswerE;
                currentSoru.setDogruSayisi(currentSoru.getDogruSayisi()+1);
                currentSoru.setSonCevap(1);
                tvDogruSayisi.setText(String.valueOf(Integer.parseInt(tvDogruSayisi.getText().toString())+1));
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            }else {
                cvChoiceE.setCardBackgroundColor(Color.RED);
                cevaplarIDs[questionCounter]=R.id.tvAnswerE;
                tvYanlisSayisi.setText(String.valueOf(Integer.parseInt(tvYanlisSayisi.getText().toString())+1));
                currentSoru.setYanlisSayisi(currentSoru.getYanlisSayisi()+1);
                currentSoru.setSonCevap(0);
                db.setSoruDogruYanlisSayisi(currentSoru);
                AnswersNonClickable();
            }
        }
    }

    public void AnswersNonClickable(){
        tvChoiceA.setOnClickListener(null);
        tvChoiceB.setOnClickListener(null);
        tvChoiceC.setOnClickListener(null);
        tvChoiceD.setOnClickListener(null);
        tvChoiceE.setOnClickListener(null);
    }
    public void AnswersClickable(){
        tvChoiceA.setOnClickListener(this);
        tvChoiceB.setOnClickListener(this);
        tvChoiceC.setOnClickListener(this);
        tvChoiceD.setOnClickListener(this);
        tvChoiceE.setOnClickListener(this);
    }
    private void setDefaultBackgroundColor(){
        cvChoiceA.setCardBackgroundColor(defaultColorCardView);
        cvChoiceB.setCardBackgroundColor(defaultColorCardView);
        cvChoiceC.setCardBackgroundColor(defaultColorCardView);
        cvChoiceD.setCardBackgroundColor(defaultColorCardView);
        cvChoiceE.setCardBackgroundColor(defaultColorCardView);
        tvChoiceA.setTextColor(Color.BLACK);
        tvChoiceB.setTextColor(Color.BLACK);
        tvChoiceC.setTextColor(Color.BLACK);
        tvChoiceD.setTextColor(Color.BLACK);
        tvChoiceE.setTextColor(Color.BLACK);
    }

    public void showFragment(Fragment fragment){
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.frame_contentMain,fragment);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }
}
