package com.oriontech.kpsswork.Interfaces;

import android.app.FragmentManager;

import com.oriontech.kpsswork.Fragments.FFavori;
import com.oriontech.kpsswork.Fragments.FHDKTestler;
import com.oriontech.kpsswork.Fragments.FHDerslerKonular;
import com.oriontech.kpsswork.Fragments.FHome;
import com.oriontech.kpsswork.Fragments.FHomeDersler;
import com.oriontech.kpsswork.Fragments.FIstatistik;
import com.oriontech.kpsswork.Fragments.FKonuAnlatim;
import com.oriontech.kpsswork.Fragments.FSoru;

/**
 *This interface, which I call Backable (I'm a stickler for naming conventions), has a single method
 * onBackPressed() that must return a boolean value. We need to enforce a boolean value because we
 * will need to know if the back button press has "absorbed" the back event. Returning true means that
 * it has, and no further action is needed, otherwise, false says that the default back action still
 * must take place. This interface should be it's own file (preferably in a separate package named interfaces).
 * @return true if the App can be closed, false otherwise
 */

public interface Backable {
    FHome fHome = new FHome();
    FHomeDersler fHomeDersler= new FHomeDersler();
    FHDerslerKonular fhDerslerKonular= new FHDerslerKonular();
    FHDKTestler fhdkTestler = new FHDKTestler();
    FIstatistik fIstatistik = new FIstatistik();
    FKonuAnlatim fKonuAnlatim = new FKonuAnlatim();
    FSoru fSoru=new FSoru();
    FFavori fFavori = new FFavori();
}
