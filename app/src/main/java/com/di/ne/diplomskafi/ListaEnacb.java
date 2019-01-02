package com.di.ne.diplomskafi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Enacba;
import com.di.ne.diplomska.SQLite.Nastavitve;

import java.io.File;
import java.util.List;

/**
 * Created by Nejc on 11.09.2016.
 */
public class ListaEnacb extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db = new DatabaseHelper(this);

    String EquFilePath = Environment.getExternalStorageDirectory() + "/DIPL/SlikeEnacbe/";
    String PhoFilePath = Environment.getExternalStorageDirectory() + "/DIPL/Slike/";
    long VelikostCrk = 20;
    double Scale = VelikostCrk/10;
    int en = 0;
    Context con = this;
    List<Enacba> enacbe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.komponenta_menijev);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.zaslon);
        ll.setBackgroundColor(Color.WHITE);

        List<Nastavitve> nast = db.readNastavitve();
        Log.d("Nasavitve", "" + nast.size());
        if (nast.size() != 0) {
            VelikostCrk = nast.get(0).getVelikostPisave();
            EquFilePath = nast.get(0).getEnacbePath();
            PhoFilePath = nast.get(0).getImagePath();
        } else {
            VelikostCrk = 20;
        }
        enacbe = db.readEnacba();

        if(enacbe.size()!=0){
            String slika="";
            File imgFile;
            for (int i=0; i<enacbe.size(); i++){
                ImageView enacba = new ImageView(this);
                slika = enacbe.get(i).getIme().replaceAll(" ", "").trim().toLowerCase();
                Log.d("Slika Poglavja:", slika);
                imgFile = new File(EquFilePath+slika);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    enacba.setImageBitmap(myBitmap);
                    int EnWi = 0;
                    int EnHe = 0;
                    if(myBitmap!=null){
                        double wi = myBitmap.getWidth();
                        double he = myBitmap.getHeight();
                        EnHe = (int)(he*Scale);
                        EnWi = (int)(wi*Scale);
                    }
                    android.view.ViewGroup.LayoutParams layoutParams = new ViewGroup.MarginLayoutParams(EnWi, EnHe);
                    enacba.setLayoutParams(layoutParams);
                    en = i;
                    enacba.setOnClickListener(this);
                    enacba.setId(i);

                    ll.addView(enacba);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("View: ",v.getId()+"");
        Intent intent = new Intent(con, KomponentaEnacb.class);
        intent.putExtra("GP","all");
        intent.putExtra("PP","all");
        intent.putExtra("ID_EN",""+enacbe.get(v.getId()).getID());
        startActivity(intent);
    }
}
