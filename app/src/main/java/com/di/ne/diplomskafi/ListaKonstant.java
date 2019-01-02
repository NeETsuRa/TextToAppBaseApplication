package com.di.ne.diplomskafi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Nastavitve;
import com.di.ne.diplomska.SQLite.OpisClenov;

import java.util.List;

/**
 * Created by Nejc on 11.09.2016.
 */
public class ListaKonstant extends AppCompatActivity {
    DatabaseHelper db = new DatabaseHelper(this);

    String EquFilePath = "/DIPL/SlikeEnacbe/";
    String PhoFilePath = "/DIPL/Slike/";
    long VelikostCrk = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.komponenta_menijev);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.zaslon);

        List<Nastavitve> nast = db.readNastavitve();
        Log.d("Nasavitve", "" + nast.size());
        if (nast.size() != 0) {
            VelikostCrk = nast.get(0).getVelikostPisave();
            EquFilePath = nast.get(0).getEnacbePath();
            PhoFilePath = nast.get(0).getImagePath();
        } else {
            VelikostCrk = 20;
        }
        Intent InputIntent = this.getIntent();
        List<OpisClenov> cleni = db.readOpisClenov();
        if (cleni.size()!=0){
            for (int i=0; i<cleni.size(); i++){
                if (cleni.get(i).getKonstanta()!=0){
                    LinearLayout vnos =new LinearLayout(this);
                    vnos.setOrientation(LinearLayout.HORIZONTAL);

                    TextView konst = new TextView(this);
                    konst.setTextSize(VelikostCrk);
                    konst.setTypeface (Typeface.DEFAULT_BOLD);
                    konst.setText(cleni.get(i).getCrka()+": ");
                    vnos.addView(konst);

                    TextView vrednost = new TextView(this);
                    vrednost.setTextSize(VelikostCrk);
                    vrednost.setTypeface (Typeface.DEFAULT_BOLD);
                    vrednost.setText(cleni.get(i).getKonstanta()+" - ");
                    vnos.addView(vrednost);

                    TextView opis = new TextView(this);
                    opis.setTextSize(VelikostCrk);
                    opis.setText(cleni.get(i).getPomen());
                    opis.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT));
                    vnos.addView(opis);

                    ll.addView(vnos);
                }
            }
        }

    }
}

