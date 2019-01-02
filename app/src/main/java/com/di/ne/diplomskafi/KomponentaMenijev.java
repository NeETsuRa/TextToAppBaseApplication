package com.di.ne.diplomskafi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.GPoglavja;
import com.di.ne.diplomska.SQLite.Nastavitve;
import com.di.ne.diplomska.SQLite.PPoglavja;

import java.io.File;
import java.util.List;

/**
 * Created by Nejc on 28.05.2016.
 * Read the database od menu. If there is an intent then the sub menu is to be displayd
 */
public class KomponentaMenijev extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db = new DatabaseHelper(this);
    List<GPoglavja> GPogl;
    List<PPoglavja> PPogl;
    String Loc;
    String EquFilePath = Environment.getExternalStorageDirectory() + "/DIPL/SlikeEnacbe/";
    String PhoFilePath = Environment.getExternalStorageDirectory() + "/DIPL/Slike/";
    long VelikostCrk = 20;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.komponenta_menijev);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            final LinearLayout ll = (LinearLayout)findViewById(R.id.zaslon);

            Intent intent = this.getIntent();
            Loc = intent.getStringExtra("run");
            List<Nastavitve> nast = db.readNastavitve();
            Log.d("Nasavitve",""+nast.size());
            if(nast.size()!=0){
                VelikostCrk = nast.get(0).getVelikostPisave();
                EquFilePath = nast.get(0).getEnacbePath();
                PhoFilePath = nast.get(0).getImagePath();
            }else {
                VelikostCrk = 20;
                EquFilePath = Environment.getExternalStorageDirectory() + "/DIPL/SlikeEnacbe/";
                PhoFilePath = Environment.getExternalStorageDirectory() + "/DIPL/Slike/";
            }

            int i=0;
            if (Loc.equals("GP")) {
                List<GPoglavja> poglavja = db.readGPoglavja();
                GPogl=poglavja;
                for (GPoglavja poglav : poglavja) {
                    LinearLayout vnos =new LinearLayout(this);
                    vnos.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView image = new ImageView(this);
                    String slika = poglav.getImeGlPoglavja().replaceAll(" ","").trim().toLowerCase();
                    File imgFile = new File(PhoFilePath+slika+".JPG");
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        image.setImageBitmap(myBitmap);
                    }else{
                        int resId = getResources().getIdentifier("kvadrat", "drawable", getPackageName());
                        image.setImageResource(resId);
                    }
                    Log.d("Slika Poglavja:",slika);
                    vnos.addView(image);
                    android.view.ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
                    layoutParams.width = 200;
                    layoutParams.height = 200;
                    image.setLayoutParams(layoutParams);

                    LinearLayout gpinopis =new LinearLayout(this);
                    gpinopis.setOrientation(LinearLayout.VERTICAL);

                    TextView txt = new TextView(this);
                    txt.setTextSize(VelikostCrk+5);
                    txt.setTypeface (Typeface.DEFAULT_BOLD);
                    txt.setOnClickListener(this);
                    txt.setId(i);
                    txt.setText(poglav.getImeGlPoglavja());
                    gpinopis.addView(txt);

                    TextView txt2 = new TextView(this);
                    txt2.setTextSize(VelikostCrk);
                    txt2.setOnClickListener(this);
                    txt2.setId(i);
                    txt2.setText(poglav.getOpisPoglavja());
                    gpinopis.addView(txt2);

                    vnos.addView(gpinopis);

                    ll.addView(vnos);
                    i++;
                }

            }
            if (Loc.equals("PP")) {
                Log.d("Info",""+Integer.parseInt(intent.getStringExtra("ID")));

                List<PPoglavja> poglavja = db.readSpecPPoglavja( Integer.parseInt(intent.getStringExtra("ID")));

                PPogl=poglavja;
                for (PPoglavja poglav : poglavja) {

                    LinearLayout vnos =new LinearLayout(this);
                    vnos.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView image = new ImageView(this);
                    String slika = poglav.getImePoPoglavja().replaceAll(" ","").trim().toLowerCase();
                    Log.d("Slika Poglavja:",slika);
                    File imgFile = new File(PhoFilePath+slika+".JPG");
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        image.setImageBitmap(myBitmap);
                    }else{
                        int resId = getResources().getIdentifier("kvadrat", "drawable", getPackageName());
                        image.setImageResource(resId);
                    }
                    vnos.addView(image);
                    android.view.ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
                    layoutParams.width = 200;
                    layoutParams.height = 200;
                    image.setLayoutParams(layoutParams);

                    LinearLayout gpinopis =new LinearLayout(this);
                    gpinopis.setOrientation(LinearLayout.VERTICAL);

                    TextView txt = new TextView(this);
                    txt.setTextSize(VelikostCrk+5);
                    txt.setTypeface (Typeface.DEFAULT_BOLD);
                    txt.setOnClickListener(this);
                    txt.setId(i);
                    txt.setText(poglav.getImePoPoglavja());
                    gpinopis.addView(txt);

                    TextView txt2 = new TextView(this);
                    txt2.setTextSize(VelikostCrk);
                    txt2.setOnClickListener(this);
                    txt2.setId(i);
                    txt2.setText(poglav.getOpisPoglavja());
                    gpinopis.addView(txt2);

                    vnos.addView(gpinopis);

                    ll.addView(vnos);
                    i++;
                }

            }

        }

    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected void onResume() {
        super.onResume();
        List<Nastavitve> nast = db.readNastavitve();
        Log.d("Nasavitve",""+nast.size());
        if(nast.size()!=0){
            VelikostCrk = nast.get(0).getVelikostPisave();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_komponenta_vsebine, menu);
        return true;
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

    @Override
    public void onClick(View v) {

        Log.d("Menu Item:", ""+v.getId());
        if(Loc.equals("GP")){
            Log.d("Info", GPogl.get(v.getId()).getImeGlPoglavja() );
            Log.d("Info",""+GPogl.get(v.getId()).getID());

            Intent intent = new Intent(this, KomponentaMenijev.class);
            intent.putExtra("run","PP");
            intent.putExtra("ID",""+GPogl.get(v.getId()).getID());
            intent.putExtra("GP",GPogl.get(v.getId()).getImeGlPoglavja());
            startActivity(intent);
        }

        if(Loc.equals("PP")){
            Log.d("Info", PPogl.get(v.getId()).getImePoPoglavja() );
            Intent intentInput = this.getIntent();
            Intent intent = new Intent(this, KomponentaVsebine.class);
            intent.putExtra("ID",""+PPogl.get(v.getId()).getID());
            intent.putExtra("GP",intentInput.getStringExtra("GP"));
            intent.putExtra("PP",PPogl.get(v.getId()).getImePoPoglavja());
            startActivity(intent);
        }

    }


}
