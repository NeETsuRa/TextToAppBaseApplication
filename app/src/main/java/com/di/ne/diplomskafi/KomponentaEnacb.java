package com.di.ne.diplomskafi;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.di.ne.diplomska.SQLite.DatabaseHelper;
import com.di.ne.diplomska.SQLite.Enacba;
import com.di.ne.diplomska.SQLite.Nastavitve;
import com.di.ne.diplomska.SQLite.OpisClenov;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nejc on 28.05.2016.
 * Calculatable equasion
 */
public class KomponentaEnacb extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db = new DatabaseHelper(this);
    String EquFilePath = Environment.getExternalStorageDirectory() + "/DIPL/SlikeEnacbe/";
    String PhoFilePath = Environment.getExternalStorageDirectory() + "/DIPL/Slike/";
    long VelikostCrk = 20;


    List<String> CleniL = new ArrayList<String>();
    List<String> CleniD = new ArrayList<String>();
    List<String> Cleni = new ArrayList<String>();
    List<EditText> allEds = new ArrayList<EditText>();
    List<TextView> allTexts = new ArrayList<TextView>();
    String LEnacba = "";
    String DEnacba = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent inputIntent = this.getIntent();
        String GLPogl = inputIntent.getStringExtra("GP");
        String POPogl = inputIntent.getStringExtra("PP");
        long ID_Enacbe = Long.parseLong(inputIntent.getStringExtra("ID_EN"));
        Log.d("Komponenta Enacb: ", "Enacba: " + ID_Enacbe + "GP: " + GLPogl + " in PP: " + POPogl);
        Enacba en = db.readSpecEnacba(ID_Enacbe);

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

        ImageView image = new ImageView(this);
        String slika = en.getIme().replaceAll(" ", "").trim().toLowerCase();
        Log.d("Slika Poglavja:", slika);
        File imgFile = new File(EquFilePath + slika);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
            int EnWi = 0;
            int EnHe = 0;
            if (myBitmap != null) {
                double wi = myBitmap.getWidth();
                double he = myBitmap.getHeight();
                EnHe = 300;
                EnWi = (int) (EnHe * (wi / he));
            }
            android.view.ViewGroup.LayoutParams layoutParams = new ViewGroup.MarginLayoutParams(EnWi, EnHe);
            image.setLayoutParams(layoutParams);
            ll.addView(image);

            String ena = en.getEnacba();
            boolean cl = false;
            String clen = "";
            List<String> cleni = new ArrayList<>();
            boolean L = true;
            String numbers = "0123456789";
            for (char letter : ena.toCharArray()) {
                if (letter == '=') {
                    L = false;
                    continue;
                }
                if (L) {
                    LEnacba += letter;
                } else {
                    DEnacba += letter;
                }
                if (letter == '|') {
                    if (cl == false) {
                        cl = true;
                        clen = "";
                        continue;
                    } else {
                        cl = false;
                        OpisClenov c = db.readSpecOpisClenov(clen);
                        if (L) {
                            CleniL.add(c.getCrka());
                        } else {
                            CleniD.add(c.getCrka());
                        }
                        if (!cleni.contains(clen)) {
                            Cleni.add(clen);
                            cleni.add(clen);
                        }
                        continue;
                    }
                }
                if (cl) {
                    clen = clen + letter;
                    continue;
                }

                if (numbers.contains(letter + "")) {
                    if (L) {
                        CleniL.add(letter + "");
                    } else {
                        CleniD.add(letter + "");
                    }
                }
            }
            Log.d("Cleni: ", "levo: " + CleniL.toString() + " desno: " + CleniD.toString());
            Log.d("Enacba: ", "levo: " + LEnacba + " desno: " + DEnacba);
            Log.d("Cleni Unique: ", Cleni.toString());

            int i = 0;
            for (String vnos : Cleni) {
                i++;
                LinearLayout vnosLayout = new LinearLayout(this);
                vnosLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView txt = new TextView(this);
                txt.setTextSize(VelikostCrk);
                txt.setOnClickListener(this);
                txt.setText(vnos + ": ");
                allTexts.add(txt);
                vnosLayout.addView(txt);

                EditText txtVnos = new EditText(this);
                txtVnos.setTextSize(VelikostCrk + 5);
                txtVnos.setId(i);
                OpisClenov c = db.readSpecOpisClenov(vnos);
                if (c.getKonstanta() != 0)
                    txtVnos.setText(c.getKonstanta() + "");
                txtVnos.setLayoutParams(new android.view.ViewGroup.LayoutParams
                        (android.view.ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                txtVnos.setMaxLines(1);
                txtVnos.setInputType(InputType.TYPE_CLASS_PHONE);
                allEds.add(txtVnos);
                vnosLayout.addView(txtVnos);

                ll.addView(vnosLayout);
            }

            Button btn = new Button(this);
            btn.setOnClickListener(this);
            btn.setText("Izracunaj");
            btn.setLayoutParams(new android.view.ViewGroup.LayoutParams
                    (android.view.ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setTextSize(VelikostCrk + 5);
            ll.addView(btn);


        }


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        Log.d("Klik: ", "st clenov: " + allEds.size());
        String stevila = "1234567890";
        HashMap<String, String> ClenVrednost = new HashMap<String, String>();
        String clenZaIzracun = "";
        double iskanaVrednost = 0;
        for (int i = 0; i < allEds.size(); i++) {
            ClenVrednost.put(allTexts.get(i).getText().toString().substring(0, (allTexts.get(i).getText().toString().length() - 2)),
                    allEds.get(i).getText().toString());

        }
        int manjkajociCleni = 0;
        for (String key : ClenVrednost.keySet()) {
            if (ClenVrednost.get(key).equals("")) {
                manjkajociCleni++;
            }
        }

        if(manjkajociCleni!=1){
            if(manjkajociCleni == 0){
                new AlertDialog.Builder(this)
                        .setTitle("Opozorilo")
                        .setMessage("Vsi cleni so izpolnjeni. Kalkulacija možna samo v primeru ko manjka natanko eden člen.")
                        .setNeutralButton("Zapri",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {/*klik*/  }})
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Opozorilo")
                        .setMessage("Preveliko stevilo neizpolnjenih členov. Kalkulacija možna samo v primeru ko manjka natanko eden člen.")
                        .setNeutralButton("Zapri",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {/*klik*/  }})
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }


        }else{
            Log.d("Vrednosti: ", ClenVrednost.toString());
            if (CleniL.size() > CleniD.size()) {
                List<String> a = CleniL;
                CleniL = CleniD;
                CleniD = a;
            }
            Log.d("Izracun: ", clenZaIzracun);
            if (LEnacba.contains("sin") && DEnacba.contains("sin")) {
                Log.d("Typ: ", "Lomni Zakon");
                String iskaniClen = "";
                for (String key : ClenVrednost.keySet()) {
                    if (ClenVrednost.get(key).equals("")) {
                        iskaniClen = key;
                    }
                }
                Log.d("Iskani Clen: ", iskaniClen);
                clenZaIzracun = iskaniClen;
                if (iskaniClen.equals("Al")) {
                    double n1 = Double.parseDouble(ClenVrednost.get("n1"));
                    double n2 = Double.parseDouble(ClenVrednost.get("n2"));
                    double Be = Double.parseDouble(ClenVrednost.get("Be"));

                    iskanaVrednost = Math.asin((Math.sin(Be) * n2) / n1);
                } else {
                    if (iskaniClen.equals("Be")) {
                        double n1 = Double.parseDouble(ClenVrednost.get("n1"));
                        double n2 = Double.parseDouble(ClenVrednost.get("n2"));
                        double Al = Double.parseDouble(ClenVrednost.get("Al"));

                        iskanaVrednost = Math.asin((Math.sin(Al) * n1) / n2);
                    } else {
                        if (iskaniClen.equals("n1")) {
                            double Be = Double.parseDouble(ClenVrednost.get("Be"));
                            double n2 = Double.parseDouble(ClenVrednost.get("n2"));
                            double Al = Double.parseDouble(ClenVrednost.get("Al"));

                            iskanaVrednost = (Math.sin(Be) * n2) / Math.sin(Al);
                        } else {//n2
                            double Be = Double.parseDouble(ClenVrednost.get("Be"));
                            double n1 = Double.parseDouble(ClenVrednost.get("n1"));
                            double Al = Double.parseDouble(ClenVrednost.get("Al"));

                            iskanaVrednost = (Math.sin(Al) * n1) / Math.sin(Be);
                        }
                    }
                }

            } else {
                if (LEnacba.contains("sin")) {
                    Log.d("Typ: ", "Interferenca");
                    String iskaniClen = "";
                    for (String key : ClenVrednost.keySet()) {
                        if (ClenVrednost.get(key).equals("")) {
                            iskaniClen = key;
                        }
                    }
                    Log.d("Iskani Clen: ", iskaniClen);
                    clenZaIzracun = iskaniClen;

                    if (iskaniClen.equals("d")) {
                        double Fi = Double.parseDouble(ClenVrednost.get("Fi"));
                        double N = Double.parseDouble(ClenVrednost.get("N"));
                        double La = Double.parseDouble(ClenVrednost.get("La"));

                        iskanaVrednost = (N * La) / Math.sin(Fi);
                    } else {
                        if (iskaniClen.equals("Fi")) {
                            double d = Double.parseDouble(ClenVrednost.get("d"));
                            double N = Double.parseDouble(ClenVrednost.get("N"));
                            double La = Double.parseDouble(ClenVrednost.get("La"));

                            iskanaVrednost = Math.asin((N * La) / d);
                        } else {
                            if (iskaniClen.equals("N")) {
                                double Fi = Double.parseDouble(ClenVrednost.get("Fi"));
                                double d = Double.parseDouble(ClenVrednost.get("d"));
                                double La = Double.parseDouble(ClenVrednost.get("La"));

                                iskanaVrednost = (d * Math.sin(Fi)) / La;
                            } else {//La
                                double Fi = Double.parseDouble(ClenVrednost.get("Fi"));
                                double d = Double.parseDouble(ClenVrednost.get("d"));
                                double N = Double.parseDouble(ClenVrednost.get("N"));

                                iskanaVrednost = (d * Math.sin(Fi)) / N;
                            }
                        }
                    }

                } else {
                    if (DEnacba.contains("sin")) {
                        Log.d("Typ: ", "Navor");
                        String iskaniClen = "";
                        for (String key : ClenVrednost.keySet()) {
                            if (ClenVrednost.get(key).equals("")) {
                                iskaniClen = key;
                            }
                        }
                        Log.d("Iskani Clen: ", iskaniClen);
                        clenZaIzracun = iskaniClen;
                        if (iskaniClen.equals("M")) {
                            double r = Double.parseDouble(ClenVrednost.get("r"));
                            double F = Double.parseDouble(ClenVrednost.get("F"));
                            double Fi = Double.parseDouble(ClenVrednost.get("Fi"));

                            iskanaVrednost = r * F * Math.sin(Fi);
                        } else {
                            if (iskaniClen.equals("r")) {
                                double M = Double.parseDouble(ClenVrednost.get("M"));
                                double F = Double.parseDouble(ClenVrednost.get("F"));
                                double Fi = Double.parseDouble(ClenVrednost.get("Fi"));
                                iskanaVrednost = M / (F * Math.sin(Fi));
                            } else {
                                if (iskaniClen.equals("F")) {
                                    double M = Double.parseDouble(ClenVrednost.get("M"));
                                    double r = Double.parseDouble(ClenVrednost.get("r"));
                                    double Fi = Double.parseDouble(ClenVrednost.get("Fi"));
                                    iskanaVrednost = M / (r * Math.sin(Fi));
                                } else {//Fi
                                    double M = Double.parseDouble(ClenVrednost.get("M"));
                                    double r = Double.parseDouble(ClenVrednost.get("r"));
                                    double F = Double.parseDouble(ClenVrednost.get("F"));
                                    iskanaVrednost = M / (r * F);
                                }
                            }
                        }

                    } else {
                        if (LEnacba.contains("+")) {
                            Log.d("Typ: ", "Preslikave Objektov");
                            String iskaniClen = "";
                            for (String key : ClenVrednost.keySet()) {
                                if (ClenVrednost.get(key).equals("")) {
                                    iskaniClen = key;
                                }
                            }
                            Log.d("Iskani Clen: ", iskaniClen);
                            clenZaIzracun = iskaniClen;

                            if (iskaniClen.equals("f")) {
                                double a = Double.parseDouble(ClenVrednost.get("a"));
                                double b = Double.parseDouble(ClenVrednost.get("b"));
                                iskanaVrednost = (a * b) / (a + b);
                            } else {
                                if (iskaniClen.equals("a")) {
                                    double f = Double.parseDouble(ClenVrednost.get("f"));
                                    double b = Double.parseDouble(ClenVrednost.get("b"));
                                    iskanaVrednost = (f * b) / (b - f);
                                } else {// b
                                    double a = Double.parseDouble(ClenVrednost.get("a"));
                                    double f = Double.parseDouble(ClenVrednost.get("f"));
                                    iskanaVrednost = (a * f) / (a - f);
                                }
                            }
                        } else {
                            Log.d("Cleni: ", "levo: " + CleniL.toString() + " desno: " + CleniD.toString());
                            String iskaniClen = "";
                            for (String key : ClenVrednost.keySet()) {
                                if (ClenVrednost.get(key).equals("")) {
                                    iskaniClen = key;
                                }
                            }
                            Log.d("Iskani Clen: ", iskaniClen);
                            clenZaIzracun = iskaniClen;

                            boolean ClLevo = false;
                            for (String cl : CleniL) {
                                if (cl.equals(iskaniClen)) {
                                    ClLevo = true;
                                }
                            }
                            if (ClLevo) {
                                Log.d("Iskani Clen: ", "se nahaja na levi strani");
                                int PonovitevIskanegaClena = 0;
                                double zmnozekDesno = 1;
                                for (String cl : CleniD) {
                                    if (stevila.contains(cl))
                                        zmnozekDesno *= Double.parseDouble(cl);
                                    else
                                        zmnozekDesno *= Double.parseDouble(ClenVrednost.get(cl));
                                }
                                double zmnozekLevo = 1;
                                for (String cl : CleniL) {
                                    if (!cl.equals(iskaniClen)) {
                                        if (stevila.contains(cl))
                                            zmnozekLevo *= Double.parseDouble(cl);
                                        else
                                            zmnozekLevo *= Double.parseDouble(ClenVrednost.get(cl));
                                    } else {
                                        PonovitevIskanegaClena++;
                                    }
                                }
                                iskanaVrednost = zmnozekDesno / zmnozekLevo;
                                iskanaVrednost = Math.pow(iskanaVrednost, (double) 1 / PonovitevIskanegaClena);

                                Log.d("Racun: ", PonovitevIskanegaClena + " ti kotrn iz " + iskanaVrednost + " = " + zmnozekDesno + " / " + zmnozekLevo);
                            } else {
                                Log.d("Iskani Clen: ", "se nahaja na desni strani");
                                int PonovitevIskanegaClena = 0;
                                double zmnozekDesno = 1;
                                for (String cl : CleniD) {
                                    if (!cl.equals(iskaniClen)) {
                                        if (stevila.contains(cl))
                                            zmnozekDesno *= Double.parseDouble(cl);
                                        else
                                            zmnozekDesno *= Double.parseDouble(ClenVrednost.get(cl));
                                    } else {
                                        PonovitevIskanegaClena++;
                                    }
                                }
                                double zmnozekLevo = 1;
                                for (String cl : CleniL) {
                                    if (stevila.contains(cl))
                                        zmnozekLevo *= Double.parseDouble(cl);
                                    else
                                        zmnozekLevo *= Double.parseDouble(ClenVrednost.get(cl));
                                }
                                iskanaVrednost = zmnozekLevo / zmnozekDesno;
                                iskanaVrednost = Math.pow(iskanaVrednost, (double) 1 / PonovitevIskanegaClena);

                                Log.d("Racun: ", PonovitevIskanegaClena + " ti kotrn iz " + iskanaVrednost + " = " + zmnozekDesno + " / " + zmnozekLevo);
                            }

                        }
                    }
                }
            }
            for (int i = 0; i < allEds.size(); i++) {
                if (allTexts.get(i).getText().toString().substring(0, (allTexts.get(i).getText().toString().length() - 2)).equals(clenZaIzracun)) {
                    Log.d("Clen za vpis je: ", allTexts.get(i).getText().toString());
                    EditText rez = (EditText) findViewById((i + 1));
                    rez.setText(iskanaVrednost + "");
                }

            }

        }
    }
}
