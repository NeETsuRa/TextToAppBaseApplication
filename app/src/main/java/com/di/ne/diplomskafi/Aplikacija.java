package com.di.ne.diplomskafi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.di.ne.diplomska.SQLite.App;
import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Enacba;
import com.di.ne.diplomska.SQLite.GPoglavja;
import com.di.ne.diplomska.SQLite.Nastavitve;
import com.di.ne.diplomska.SQLite.OpisClenov;
import com.di.ne.diplomska.SQLite.PPoglavja;
import com.di.ne.diplomska.SQLite.VsebinaPoglavja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/*
* Read TXT File and start the 1 Menu Part
* */
public class Aplikacija extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    final Context con = this;
    Enacba ena = new Enacba();
    GPoglavja glpoglavje = new GPoglavja();
    Nastavitve nast = new Nastavitve();
    PPoglavja podpoglavja = new PPoglavja();
    Reklame rekl = new Reklame();
    VsebinaPoglavja vsebina = new VsebinaPoglavja();
    App appl = new App();
    String File = Environment.getExternalStorageDirectory()+"/DIPL/InputFile.txt";

    boolean reklame = false;
    long delay = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Nastavitve> nast = db.readNastavitve();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }


        if(nast.size()!=0){
            File = nast.get(0).getFilePath();
        }else {
            File = Environment.getExternalStorageDirectory()+"/DIPL/InputFile.txt";
        }
        Log.d("Besedilo", "Zagon");
        if (reklame){
            reklame();
        }
        File Input = new File(File);
        if(!Input.exists()){
            Intent intent = new Intent(this, KomponentaNastavitev.class);
            startActivity(intent);
            System.exit(0);
        }
        String line = "";
        String fullTXT = "";
        setContentView(R.layout.activity_komponenta_vsebine);


        TextView prikaz = (TextView)findViewById(R.id.Besedilo2);
        StringBuilder sb = new StringBuilder();

        Intent intentInput = this.getIntent();
        boolean update = false;
        List<App> aplikacije = db.readApp();
        try{
            BufferedReader br = new BufferedReader(new FileReader(Input));
            int i = 0;
            String parent = "";
            while ((line = br.readLine())!=null) {
                if (i == 0) {
                    if(aplikacije.size()!=0){
                        fullTXT = fullTXT + "Verzija Datoteke : " + line + " Verzija baze: " + aplikacije.get(0).getVerzija() + "\n";
                        parent = "Ver";
                        if (Long.parseLong(line) != aplikacije.get(0).getVerzija()) {
                            Log.d("Verzija", "Nova verzija");
                            update = true;
                        } else {
                            Log.d("Verzija", "Obstojeca verzija");
                            if(intentInput.hasExtra("Par")) {
                                if (intentInput.getStringExtra("Par").equals("Branje")) {
                                    update = true;
                                }
                            }
                        }
                    }else{
                        Log.d("Verzija", "Nova verzija");
                        update = true;
                    }
                    if(update){
                        appl.setVerzija(Long.parseLong(line));
                        db.clearOpisClenov();
                        db.clearEnacba();
                        db.clearGPoglavja();
                        db.clearNastavitve();
                        db.clearPPoglavja();
                        db.clearVsebinaPoglavja();
                        db.clearReklame();
                        db.clearApp();
                    }
                }
                if (i == 1 && update) {
                    fullTXT = fullTXT + "Ime Aplikacije: " + line + "\n";
                    parent = "Naslov";
                    appl.setImeAplikacije(line);
                    db.writeApp(appl);
                } else if (line.contains("/g/") && update) {
                    glpoglavje.setOpisPoglavja("");
                    glpoglavje.setImeGlPoglavja("");
                    glpoglavje.setID(0);
                    if (parent != "EN" && parent != "CL" && parent != "Naslov") {
                        db.writeVsebinaPoglavja(vsebina);
                    }
                    line = line.substring(3, line.length());
                    if (line.length() != 0) {
                        fullTXT = fullTXT + "Glavno poglavje: " + line + "\n";
                        glpoglavje.setImeGlPoglavja(line);
                    }
                    parent = "GP";
                } else if (line.contains("/p/") && update) {
                    podpoglavja.setID_GPoglavja(0);
                    podpoglavja.setID(0);
                    podpoglavja.setOpisPoglavja("");
                    podpoglavja.setImePoPoglavja("");
                    if (parent == "GP") {
                        long ID = db.writeGPoglavja(glpoglavje);
                        glpoglavje.setID(ID);
                    }
                    if (parent != "EN" && parent != "CL" && parent != "Naslov" && parent != "GP") {
                        db.writeVsebinaPoglavja(vsebina);
                    }
                    line = line.substring(3, line.length());
                    if (line.length() != 0) {
                        fullTXT = fullTXT + "Podpoglavje: " + line + "\n";
                        podpoglavja.setImePoPoglavja(line);
                        podpoglavja.setID_GPoglavja(glpoglavje.getID());
                    }
                    parent = "PP";
                } else if (line.contains("/v/") && update) {
                    vsebina.setID(0);
                    vsebina.setID_Enacbe(0);
                    vsebina.setID_PPoglavja(0);
                    vsebina.setSlika("");
                    vsebina.setOpis("");
                    if (parent == "GP") {
                        Toast.makeText(getApplicationContext(), "Error. Za podrocjem Glavnih poglavij se nesme nahajati vsebina", Toast.LENGTH_LONG);
                    }
                    if (parent == "PP") {
                        long ID = db.writePPoglavja(podpoglavja);
                        podpoglavja.setID(ID);
                    }
                    line = line.substring(3, line.length());
                    if (line.length() != 0) {
                        fullTXT = fullTXT + "Vsebina: " + line + "\n";
                        vsebina.setOpis(vsebina.getOpis() + line);
                        vsebina.setID_PPoglavja(podpoglavja.getID());
                    }
                    parent = "VS";
                } else if (line.contains("/s/") && update) {
                    line = line.substring(3, line.length());
                    if (line.length() != 0) {
                        fullTXT = fullTXT + "Slika: " + line + "\n";
                        vsebina.setSlika(line);
                    }
                    parent = "SL";
                } else if (line.contains("/e/") && update) {
                    ena.setEnacba("");
                    ena.setIme("");
                    ena.setID(0);
                    line = line.substring(3, line.length());
                    if (line.length() != 0) {
                        fullTXT = fullTXT + "Enacba: " + line + "\n";
                        ena.setIme(line);
                    } else {
                        db.writeVsebinaPoglavja(vsebina);
                    }
                    parent = "EN";
                } else {
                    if(update){
                        fullTXT = fullTXT + line + "\n";
                        if (parent == "GP") {
                            glpoglavje.setOpisPoglavja(glpoglavje.getOpisPoglavja() + line);
                        } else if (parent == "PP") {
                            podpoglavja.setOpisPoglavja(podpoglavja.getOpisPoglavja() + line);
                        } else if (parent == "VS") {
                            vsebina.setOpis(vsebina.getOpis() + line);
                        } else if (parent == "EN") {
                            parent = "CL";
                            ena.setEnacba(line);
                            long ID = db.writeEnacba(ena);
                            vsebina.setID_Enacbe(ID);
                            db.writeVsebinaPoglavja(vsebina);
                        } else if (parent == "CL") {
                            String cl = "";
                            String op = "";
                            String ko = "";
                            boolean isCl = false;
                            boolean isOp = false;
                            boolean isKo = false;
                            for (char letter : line.toCharArray()) {
                                if (letter == '|') {
                                    if (isCl == false) {
                                        isCl = true;
                                        continue;
                                    } else {
                                        isCl = false;
                                        isOp = true;
                                        continue;
                                    }
                                }
                                if (letter == '(') {
                                    isOp = false;
                                    isKo = true;
                                    ko = "";
                                    continue;
                                }
                                if (letter == ')') {
                                    isKo = false;
                                }
                                if (isCl) {
                                    cl = cl + letter;
                                }
                                if (isKo) {
                                    ko = ko + letter;
                                }
                                if (isOp) {
                                    op = op + letter;
                                }
                            }

                            OpisClenov clen = new OpisClenov();
                            clen.setPomen(op);
                            clen.setCrka(cl);
                            try
                            {
                                if (ko != "") {
                                    clen.setKonstanta(Double.parseDouble(ko.trim()));
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                clen.setPomen(op + " (" + ko + ")");
                            }

                            Log.d("Clen: ", clen.getCrka() + " Naziv: " + clen.getPomen() + " Konstanta: " + clen.getKonstanta());
                            db.writeOpisClenov(clen);
                        }
                    }
                }

                i++;
            }
            br.close();
            prikaz.setText(fullTXT);
        }catch (IOException e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);
            Log.d("Besedilo", "Napaka na branju");
        }

        Intent intent = new Intent(this, KomponentaMenijev.class);
        intent.putExtra("run","GP");
        startActivity(intent);

    }

    private void reklame() {

        new Thread(new Runnable() {
            public void run(){
                while(true){
                    SystemClock.sleep(delay);
                    Intent intent = new Intent(con, Reklame.class);
                    startActivity(intent);
                }

            }
        }).start();


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
