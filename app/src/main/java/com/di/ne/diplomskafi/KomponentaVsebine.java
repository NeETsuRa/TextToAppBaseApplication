package com.di.ne.diplomskafi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Enacba;
import com.di.ne.diplomska.SQLite.Nastavitve;
import com.di.ne.diplomska.SQLite.OpisClenov;
import com.di.ne.diplomska.SQLite.PPoglavja;
import com.di.ne.diplomska.SQLite.VsebinaPoglavja;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nejc on 28.05.2016.
 * Read the DB and show the photos and text and equasions
 */
public class KomponentaVsebine extends AppCompatActivity {
    DatabaseHelper db = new DatabaseHelper(this);
    long VelikostCrk=22;
    String EquFilePath = Environment.getExternalStorageDirectory() + "/DIPL/SlikeEnacbe/";
    String PhoFilePath = Environment.getExternalStorageDirectory() + "/DIPL/Slike/";

    long ID_Enacbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.komponenta_vsebine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        VsebinaPoglavja vse = db.readSpecVsebinaPoglavja(Integer.parseInt(intent.getStringExtra("ID")));
        String GLPogl = intent.getStringExtra("GP");
        String POPogl = intent.getStringExtra("PP");
        List<Nastavitve> nast = db.readNastavitve();
        if(nast.size()!=0){
            VelikostCrk = nast.get(0).getVelikostPisave();
            EquFilePath = nast.get(0).getEnacbePath();
            PhoFilePath = nast.get(0).getImagePath();
        }else {
            VelikostCrk = 20;
            EquFilePath = Environment.getExternalStorageDirectory() + "/DIPL/SlikeEnacbe/";
            PhoFilePath = Environment.getExternalStorageDirectory() + "/DIPL/Slike/";
        }

        ImageView logo = (ImageView)findViewById(R.id.Logo);
        ImageView slikaVsebine = (ImageView)findViewById(R.id.Slika);
        ImageView enacba = (ImageView)findViewById(R.id.Enacba);
        TextView gp = (TextView)findViewById(R.id.GP);
        TextView pp = (TextView)findViewById(R.id.PP);
        TextView vsebina = (TextView)findViewById(R.id.Vsebina);
        TextView clenienacbe = (TextView)findViewById(R.id.CleniEnacbe);

        String slika = POPogl.replaceAll(" ","").trim().toLowerCase();
        Log.d("Slika Poglavja:",slika);
        File imgFile = new File(PhoFilePath+slika+".JPG");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            logo.setImageBitmap(myBitmap);
        }else{
            int resId = getResources().getIdentifier("kvadrat", "drawable", getPackageName());
            logo.setImageResource(resId);
        }
        android.view.ViewGroup.LayoutParams layoutParams;
        logo.setLayoutParams(new LinearLayout.LayoutParams(200,200));

        slika = vse.getSlika().replaceAll(" ", "").trim().toLowerCase();
        imgFile = new File(PhoFilePath+slika);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            slikaVsebine.setImageBitmap(myBitmap);
            int EnWi = 0;
            int EnHe = 0;
            if(myBitmap!=null){
                double wi = myBitmap.getWidth();
                double he = myBitmap.getHeight();
                EnWi = (int)(getResources().getDisplayMetrics().widthPixels);
                EnHe = (int)(EnWi*(he/wi));
            }
            slikaVsebine.setLayoutParams(new LinearLayout.LayoutParams(EnWi,EnHe));
        }else{
            slikaVsebine.setVisibility(View.GONE);
        }

        if(vse.getID_Enacbe()!=0) {
            Enacba en = db.readSpecEnacba(vse.getID_Enacbe());
            ID_Enacbe = vse.getID_Enacbe();
            slika = en.getIme().replaceAll(" ", "").trim().toLowerCase();
            Log.d("Slika Poglavja:", slika);
            imgFile = new File(EquFilePath+slika);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                enacba.setImageBitmap(myBitmap);
                layoutParams = enacba.getLayoutParams();
                int EnWi = 0;
                int EnHe = 0;
                if(myBitmap!=null){
                    double wi = myBitmap.getWidth();
                    double he = myBitmap.getHeight();
                    EnHe = 300;
                    EnWi = (int)(EnHe*(wi/he));
                }
                layoutParams.width = EnWi;
                layoutParams.height = EnHe;
                enacba.setLayoutParams(layoutParams);

                String ena = en.getEnacba();
                String res = "";
                boolean cl = false;
                String clen = "";
                List<String> cleni = new ArrayList<>();
                for (char letter : ena.toCharArray()) {
                    if(letter == '|'){
                        if(cl==false) {
                            cl = true;
                            clen = "";
                            continue;
                        }else {
                            cl = false;
                            if(!cleni.contains(clen.toString())){
                                OpisClenov c = db.readSpecOpisClenov(clen);
                                res = res + c.getCrka() + ": " + c.getPomen() + "\n";
                                Log.d("Konstanta: ", ""+c.getKonstanta());
                                cleni.add(clen);
                            }
                            continue;
                        }
                    }
                    if(cl){
                        clen = clen + letter;
                    }
                }
                clenienacbe.setText(res);
            }

        }else{
            clenienacbe.setVisibility(View.GONE);
            enacba.setVisibility(View.GONE);
        }

        gp.setText(GLPogl);
        gp.setTextSize(VelikostCrk+5);

        pp.setText(POPogl);
        pp.setTextSize(VelikostCrk);

        vsebina.setText(vse.getOpis());
        vsebina.setTextSize(VelikostCrk);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_komponenta_vsebine, menu);
        return true;
    }

    public void Enacba (View v){
        Intent intent = new Intent(this, KomponentaEnacb.class);
        Intent inputIntent = this.getIntent();
        String GLPogl = inputIntent.getStringExtra("GP");
        String POPogl = inputIntent.getStringExtra("PP");
        intent.putExtra("GP",GLPogl);
        intent.putExtra("PP",POPogl);
        intent.putExtra("ID_EN",""+ID_Enacbe);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, KomponentaNastavitev.class);
            startActivity(intent);
        }

        if (id == R.id.action_ListaEnacb) {
            Intent Intent = new Intent(this, ListaEnacb.class);
            startActivity(Intent);
        }
        if (id == R.id.action_ListaKonstant) {
            Intent Intent = new Intent(this, ListaKonstant.class);
            startActivity(Intent);
        }
        if (id == R.id.action_Osvezi) {
            recreate();
        }
        if (id == R.id.action_VnosReklam) {
            Intent intent = new Intent(this, VnosReklam.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
