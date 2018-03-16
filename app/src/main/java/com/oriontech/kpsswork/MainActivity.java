package com.oriontech.kpsswork;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.oriontech.kpsswork.DB.DBHelper;
import com.oriontech.kpsswork.Fragments.FIstatistik;
import com.oriontech.kpsswork.Fragments.FKonuAnlatim;
import com.oriontech.kpsswork.Interfaces.Backable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Backable{
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout frameLayout_Content;
    DBHelper mDabase;
    int close=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigation();
        frameLayout_Content = findViewById(R.id.frame_contentMain);
        mDabase=new DBHelper(this);
        mDabase.createDB();

        showFragment(fHome,"home");

    }

    public void setupNavigation(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount()-1;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (count==0){
            close++;
            if(close==1) {
                Toast.makeText(this, "Press Back to Exit", Toast.LENGTH_SHORT).show();
            }else if(close==2){
                finish();
            }
            return;
        }else if(count>0){
            getFragmentManager().popBackStack();
        }else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Sinavlar) {
            showFragment(fHome,"home");

        } else if (id == R.id.nav_Konular) {
            showFragment(new FKonuAnlatim(),"konu");
        } else if (id == R.id.nav_Istatistik) {
            showFragment(new FIstatistik(),"istatistik");
        } else if (id == R.id.nav_Puan) {

        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFragment(Fragment fragment,String tag){
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.frame_contentMain,fragment,tag);
        fTransaction.addToBackStack(tag);
        fTransaction.commit();
    }

}
