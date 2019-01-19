package com.di.ne.diplomskafi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Reklame;

import java.util.List;

/**
 * Created by Nejc on 11.09.2016.
 */
public class VnosReklam extends AppCompatActivity {
    DatabaseHelper db = new DatabaseHelper(this);



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.komponenta_vnos_reklam);

        List<com.di.ne.diplomska.SQLite.Reklame> rekl = db.readReklame();
        if (rekl.size()!=0){
            EditText naslov = (EditText)findViewById(R.id.naslov);
            EditText vsebina = (EditText)findViewById(R.id.vsebina);
            EditText url = (EditText)findViewById(R.id.url);
            TextView slika = (TextView)findViewById(R.id.slika);

            naslov.setText(rekl.get(0).getNaslov());
            vsebina.setText(rekl.get(0).getVsebina());
            url.setText(rekl.get(0).getURL());
            slika.setText(rekl.get(0).getSlika());
        }

    }
    public void PrevzamiReklamo (View e){
        db.clearReklame();

        EditText naslov = (EditText)findViewById(R.id.naslov);
        EditText vsebina = (EditText)findViewById(R.id.vsebina);
        EditText url = (EditText)findViewById(R.id.url);
        TextView slika = (TextView)findViewById(R.id.slika);

        Reklame rekl = new Reklame();
        rekl.setNaslov(naslov.getText().toString());
        rekl.setVsebina(vsebina.getText().toString());
        rekl.setURL(url.getText().toString());
        rekl.setSlika(slika.getText().toString());
        db.writeReklame(rekl);
    }
    public void PobrisiReklame (View e){
        EditText naslov = (EditText)findViewById(R.id.naslov);
        EditText vsebina = (EditText)findViewById(R.id.vsebina);
        EditText url = (EditText)findViewById(R.id.url);
        TextView slika = (TextView)findViewById(R.id.slika);

        naslov.setText("");
        vsebina.setText("");
        url.setText("");
        slika.setText("");
        db.clearReklame();
    }
}
