package com.di.ne.diplomska.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.di.ne.diplomskafi.Aplikacija;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nejc on 02.06.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BazaApplikacije";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate", "TASK");
        // Ustvarimo Tabele
        db.execSQL(App.CREATE_TABLE_APP);
        db.execSQL(Enacba.CREATE_TABLE_ENACBA);
        db.execSQL(GPoglavja.CREATE_TABLE_GLPOGLAVJA);
        db.execSQL(Reklame.CREATE_TABLE_REKLAME);
        db.execSQL(Nastavitve.CREATE_TABLE_NASTAVITVE);
        db.execSQL(OpisClenov.CREATE_TABLE_OPISCLENOV);
        db.execSQL(PPoglavja.CREATE_TABLE_PPOGLAVJA);
        db.execSQL(VsebinaPoglavja.CREATE_TABLE_VSEBINAPOGLAVJA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade", "TASK");
        // Pri onUpgrade zbrisemo vse tabele
        db.execSQL("DROP TABLE IF EXISTS " + App.TABELA_APP);
        db.execSQL("DROP TABLE IF EXISTS " + Enacba.TABELA_ENACBA);
        db.execSQL("DROP TABLE IF EXISTS " + GPoglavja.TABELA_GLPOGLAVJA);
        db.execSQL("DROP TABLE IF EXISTS " + Reklame.TABELA_REKLAME);
        db.execSQL("DROP TABLE IF EXISTS " + Nastavitve.TABELA_NASTAVITVE);
        db.execSQL("DROP TABLE IF EXISTS " + OpisClenov.TABELA_OPISCLENOV);
        db.execSQL("DROP TABLE IF EXISTS " + PPoglavja.TABELA_PPOGLAVJA);
        db.execSQL("DROP TABLE IF EXISTS " + VsebinaPoglavja.TABELA_VSEBINAPOGLAVJA);

        // Ter jih na novo ustvarimo
        onCreate(db);
    }

    //Funkcije za vpis podatkov v Bazo
    public long writeApp (App aplikacija) {
        long ID;
        Log.d("writeApp", aplikacija.toString());
        // 1. get writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues
        ContentValues values = new ContentValues();
        values.put(App.KEY_IMEAPLIKACIJE, aplikacija.getImeAplikacije());
        values.put(App.KEY_VERZIJA, aplikacija.getVerzija());

        // 3. insert
        ID=db.insert(App.TABELA_APP, // tabela
                null,
                values);

        // 4. close
        db.close();

        return ID;
    }
    public long writeEnacba ( Enacba enacba) {
        long ID;
        Log.d("writeEnacba", enacba.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Enacba.KEY_IME, enacba.getIme());
        values.put(Enacba.KEY_ENACBA, enacba.getEnacba());
        ID=db.insert(Enacba.TABELA_ENACBA, null, values);
        db.close();
        return ID;
    }
    public long writeGPoglavja ( GPoglavja poglavje) {
        long ID;
        Log.d("writeGPoglavja", poglavje.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GPoglavja.KEY_IMEGLPOGLAVJA, poglavje.getImeGlPoglavja());
        values.put(GPoglavja.KEY_OPISPOGLAVJA, poglavje.getOpisPoglavja());
        ID=db.insert(GPoglavja.TABELA_GLPOGLAVJA, null, values);
        db.close();
        return ID;
    }
    public long writeReklame ( Reklame reklame) {
        long ID;
        Log.d("writeReklame", reklame.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Reklame.KEY_NASLOV, reklame.getNaslov());
        values.put(Reklame.KEY_SLIKA, reklame.getSlika());
        values.put(Reklame.KEY_URL, reklame.getURL());
        values.put(Reklame.KEY_VSEBINA, reklame.getVsebina());
        ID=db.insert(Reklame.TABELA_REKLAME, null, values);
        db.close();
        return ID;
    }
    public long writeOpisClenov ( OpisClenov clen) {
        long ID;
        Log.d("writeOpisClenov", clen.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OpisClenov.KEY_CRKA, clen.getCrka());
        values.put(OpisClenov.KEY_KONSTANTA, clen.getKonstanta());
        values.put(OpisClenov.KEY_POMEN, clen.getPomen());
        ID=db.insert(OpisClenov.TABELA_OPISCLENOV, null, values);
        db.close();
        return ID;
    }
    public long writePPoglavja (PPoglavja poglavje) {
        long ID;
        Log.d("writePPoglavja", poglavje.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PPoglavja.KEY_IMEPOGLAVJA, poglavje.getImePoPoglavja());
        values.put(PPoglavja.KEY_OPISPOGLAVJA, poglavje.getOpisPoglavja());
        values.put(PPoglavja.KEY_ID_GPOGLAVJA, poglavje.getID_GPoglavja());
        ID=db.insert(PPoglavja.TABELA_PPOGLAVJA, null, values);
        db.close();
        return ID;
    }
    public long writeVsebinaPoglavja ( VsebinaPoglavja vsebina) {
        long ID;
        Log.d("writeVsebinaPoglavja", vsebina.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VsebinaPoglavja.KEY_ID_ENACBE, vsebina.getID_Enacbe());
        values.put(VsebinaPoglavja.KEY_ID_PPOGLAVJA, vsebina.getID_PPoglavja());
        values.put(VsebinaPoglavja.KEY_OPIS, vsebina.getOpis());
        values.put(VsebinaPoglavja.KEY_SLIKA, vsebina.getSlika());
        ID=db.insert(VsebinaPoglavja.TABELA_VSEBINAPOGLAVJA, null, values);
        db.close();
        return ID;
    }
    public long writeNastavitve (Nastavitve nastavitve){
        long ID;
        Log.d("writeNastavitve", nastavitve.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Nastavitve.KEY_BARVA, nastavitve.getBarva());
        values.put(Nastavitve.KEY_VELIKOSTPISAVE, nastavitve.getVelikostPisave());
        values.put(Nastavitve.KEY_ENACBEPATH, nastavitve.getEnacbePath());
        values.put(Nastavitve.KEY_IMAGEPATH, nastavitve.getImagePath());
        values.put(Nastavitve.KEY_FILEPATH, nastavitve.getFilePath());
        ID=db.insert(Nastavitve.TABELA_NASTAVITVE, null, values);
        db.close();
        return ID;
    }

    //Funkcije za branje celotne tabele iz baze
    public List<App> readApp(){
        Log.d("readApp", "READ");
        List<App> list = new LinkedList<App>();

        // 1. build the query
        String query = "SELECT  * FROM " + App.TABELA_APP;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row
        App element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new App();
                //KEY_ID,   KEY_VERZIJA,    KEY_IMEAPLIKACIJE
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setVerzija(Integer.parseInt(cursor.getString(1)));
                element.setImeAplikacije(cursor.getString(2));

                // Add lo list
                list.add(element);
            } while (cursor.moveToNext());
        }

        Log.d("readApp()", list.toString());

        // return
        return list;
    }
    public List<Enacba> readEnacba(){
        Log.d("readEnacba", "READ");
        List<Enacba> list = new LinkedList<Enacba>();
        String query = "SELECT  * FROM " + Enacba.TABELA_ENACBA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Enacba element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new Enacba();
                //KEY_ID,    KEY_IME,    KEY_ENACBA
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setIme(cursor.getString(1));
                element.setEnacba(cursor.getString(2));
                list.add(element);
            } while (cursor.moveToNext());
        }
        Log.d("readEnacba()", list.toString());
        return list;
    }
    public List<GPoglavja> readGPoglavja(){
        Log.d("readGPoglavja", "READ");
        List<GPoglavja> list = new LinkedList<GPoglavja>();
        String query = "SELECT  * FROM " + GPoglavja.TABELA_GLPOGLAVJA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        GPoglavja element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new GPoglavja();
                //KEY_ID,    KEY_IMEGLPOGLAVJA,  KEY_OPISPOGLAVJA
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setImeGlPoglavja(cursor.getString(1));
                element.setOpisPoglavja(cursor.getString(2));
                list.add(element);
            } while (cursor.moveToNext());
        }
        Log.d("readGPoglavja()", list.toString());
        return list;
    }
    public List<OpisClenov> readOpisClenov(){
        Log.d("readOpisClenov", "READ");
        List<OpisClenov> list = new LinkedList<OpisClenov>();
        String query = "SELECT  * FROM " + OpisClenov.TABELA_OPISCLENOV;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        OpisClenov element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new OpisClenov();
                //KEY_ID,    KEY_CRKA,   KEY_POMEN,  KEY_KONSTANTA
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setCrka(cursor.getString(1));
                element.setPomen(cursor.getString(2));
                element.setKonstanta(Double.parseDouble(cursor.getString(3)));
                list.add(element);
            } while (cursor.moveToNext());
        }
        Log.d("readOpisClenov()", list.toString());
        return list;
    }
    public List<PPoglavja> readPPoglavja(){
        Log.d("readPPoglavja", "READ");
        List<PPoglavja> list = new LinkedList<PPoglavja>();
        String query = "SELECT  * FROM " + PPoglavja.TABELA_PPOGLAVJA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        PPoglavja element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new PPoglavja();
                //KEY_ID,    KEY_IMEPOGLAVJA,    KEY_OPISPOGLAVJA,   KEY_ID_GPOGLAVJA
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setImePoPoglavja(cursor.getString(1));
                element.setOpisPoglavja(cursor.getString(2));
                element.setID_GPoglavja(Integer.parseInt(cursor.getString(3)));
                list.add(element);
            } while (cursor.moveToNext());
        }
        Log.d("readPPoglavja()", list.toString());
        return list;
    }
    public List<VsebinaPoglavja> readVsebinaPoglavja(){
        Log.d("readVsebinaPoglavja", "READ");
        List<VsebinaPoglavja> list = new LinkedList<VsebinaPoglavja>();
        String query = "SELECT  * FROM " + VsebinaPoglavja.TABELA_VSEBINAPOGLAVJA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        VsebinaPoglavja element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new VsebinaPoglavja();
                //KEY_ID,    KEY_OPIS,   KEY_SLIKA,  KEY_ID_PPOGLAVJA,   KEY_ID_ENACBE
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setOpis(cursor.getString(1));
                element.setSlika(cursor.getString(2));
                element.setID_PPoglavja(Integer.parseInt(cursor.getString(3)));
                element.setID_Enacbe(Integer.parseInt(cursor.getString(4)));
                list.add(element);
            } while (cursor.moveToNext());
        }
        Log.d("readVsebinaPoglavja()", list.toString());
        return list;
    }
    public List<Nastavitve> readNastavitve(){
        Log.d("readNastavitve", "READ");
        List<Nastavitve> list = new LinkedList<Nastavitve>();
        String query = "SELECT  * FROM " + Nastavitve.TABELA_NASTAVITVE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Nastavitve element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new Nastavitve();
                //KEY_ID     KEY_VELIKOSTPISAVE  KEY_BARVA    KEY_FILEPATH   KEY_IMAGEPATH   KEY_ENACBEPATH
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setVelikostPisave(Integer.parseInt(cursor.getString(1)));
                element.setBarva(cursor.getString(2));
                element.setFilePath(cursor.getString(3));
                element.setImagePath(cursor.getString(4));
                element.setEnacbePath(cursor.getString(5));
                list.add(element);
            } while (cursor.moveToNext());
        }
        Log.d("readNastavitve()", list.toString());
        return list;
    }
    public List<Reklame> readReklame(){
        Log.d("readReklame", "READ");
        List<Reklame> list = new LinkedList<Reklame>();
        String query = "SELECT  * FROM " + Reklame.TABELA_REKLAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Reklame element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new Reklame();
                //KEY_ID,    KEY_NASLOV, KEY_VSEBINA,    KEY_URL,   KEY_SLIKA
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setNaslov(cursor.getString(1));
                element.setVsebina(cursor.getString(2));
                element.setURL(cursor.getString(3));
                element.setSlika(cursor.getString(4));
                list.add(element);
            } while (cursor.moveToNext());
        }
        Log.d("readReklame()", list.toString());
        return list;
    }

    //Posebna banja iz podatkovne baze (specificna uporaba)
    public Enacba readSpecEnacba(long id){
        Log.d("readSpecEnacba("+id+")", "READ");
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(Enacba.TABELA_ENACBA, // a. table
                        Enacba.COLUMNS, // b. column names
                        " " + Enacba.KEY_ID + " = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build object
        Enacba element = new Enacba();
        //KEY_ID,   KEY_IME,    KEY_ENACBA
        element.setID(Integer.parseInt(cursor.getString(0)));
        element.setIme(cursor.getString(1));
        element.setEnacba(cursor.getString(2));

        Log.d("readSpecEnacba("+id+")", element.toString());

        // 5. return
        return element;
    }
    public OpisClenov readSpecOpisClenov(String crka){
        Log.d("readSpecOpisClenov("+crka+")", "READ");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(OpisClenov.TABELA_OPISCLENOV,
                        OpisClenov.COLUMNS,
                        " " + OpisClenov.KEY_CRKA + " = ?",
                        new String[] { crka },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        OpisClenov element = new OpisClenov();
        //KEY_ID,   KEY_CRKA,   KEY_POMEN,  KEY_KONSTANTA
        element.setID(Integer.parseInt(cursor.getString(0)));
        element.setCrka(cursor.getString(1));
        element.setPomen(cursor.getString(2));
        element.setKonstanta(Double.parseDouble(cursor.getString(3)));
        Log.d("readSpecOpisClenov("+crka+")", element.toString());
        return element;
    }
    public List<PPoglavja> readSpecPPoglavja(long id){ //iskanje glede na glavno poglavje
        Log.d("readSpecPPoglavja("+id+")", "READ");
        SQLiteDatabase db = this.getReadableDatabase();
        List<PPoglavja> list = new LinkedList<PPoglavja>();
        Cursor cursor =
                db.query(PPoglavja.TABELA_PPOGLAVJA,PPoglavja.COLUMNS, " " + PPoglavja.KEY_ID_GPOGLAVJA + " = ?",
                        new String[] { String.valueOf(id) },null,null,null,null);
        PPoglavja element = null;
        if (cursor.moveToFirst()) {
            do {
                element = new PPoglavja();
                //KEY_ID,    KEY_IMEPOGLAVJA,    KEY_OPISPOGLAVJA,   KEY_ID_GPOGLAVJA
                element.setID(Integer.parseInt(cursor.getString(0)));
                element.setImePoPoglavja(cursor.getString(1));
                element.setOpisPoglavja(cursor.getString(2));
                element.setID_GPoglavja(Integer.parseInt(cursor.getString(3)));
                list.add(element);
            } while (cursor.moveToNext());
        }
        return list;
    }
    public VsebinaPoglavja readSpecVsebinaPoglavja(long id){//iskanje glede na izbrano podpoglavje
        Log.d("readSpecVsebinaPogl("+id+")", "READ");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(VsebinaPoglavja.TABELA_VSEBINAPOGLAVJA,VsebinaPoglavja.COLUMNS, " " + VsebinaPoglavja.KEY_ID_PPOGLAVJA + " = ?",
                        new String[] { String.valueOf(id) },null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        VsebinaPoglavja element = new VsebinaPoglavja();
        //KEY_ID,    KEY_OPIS,   KEY_SLIKA,  KEY_ID_PPOGLAVJA,   KEY_ID_ENACBE
        element.setID(Integer.parseInt(cursor.getString(0)));
        element.setOpis(cursor.getString(1));
        element.setSlika(cursor.getString(2));
        element.setID_PPoglavja(Integer.parseInt(cursor.getString(3)));
        element.setID_Enacbe(Integer.parseInt(cursor.getString(4)));
        Log.d("readSpecVsebinaPogl("+id+")", element.toString());
        return element;
    }

    //Brisanje podatkov iz tabel (brisemo celotno tabelo)
    public void clearApp () {
        Log.d("clearApp", "CLEAR");
        // 1. get writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete table
        db.execSQL("DROP TABLE IF EXISTS " + App.TABELA_APP);

        // 3. create empty table
        db.execSQL(App.CREATE_TABLE_APP);

        // 4. close
        db.close();

    }
    public void clearEnacba () {
        Log.d("clearEnacba", "CLEAR");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Enacba.TABELA_ENACBA);
        db.execSQL(Enacba.CREATE_TABLE_ENACBA);
        db.close();
    }
    public void clearGPoglavja () {
        Log.d("clearGPoglavja", "CLEAR");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + GPoglavja.TABELA_GLPOGLAVJA);
        db.execSQL(GPoglavja.CREATE_TABLE_GLPOGLAVJA);
        db.close();
    }
    public void clearReklame () {
        Log.d("clearReklame", "CLEAR");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Reklame.TABELA_REKLAME);
        db.execSQL(Reklame.CREATE_TABLE_REKLAME);
        db.close();
    }
    public void clearOpisClenov () {
        Log.d("clearOpisClenov", "CLEAR");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + OpisClenov.TABELA_OPISCLENOV);
        db.execSQL(OpisClenov.CREATE_TABLE_OPISCLENOV);
        db.close();
    }
    public void clearPPoglavja () {
        Log.d("clearPPoglavja", "CLEAR");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + PPoglavja.TABELA_PPOGLAVJA);
        db.execSQL(PPoglavja.CREATE_TABLE_PPOGLAVJA);
        db.close();
    }
    public void clearVsebinaPoglavja () {
        Log.d("clearVsebinaPoglavja", "CLEAR");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + VsebinaPoglavja.TABELA_VSEBINAPOGLAVJA);
        db.execSQL(VsebinaPoglavja.CREATE_TABLE_VSEBINAPOGLAVJA);
        db.close();
    }
    public void clearNastavitve (){
        Log.d("clearNastavitve", "CLEAR");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Nastavitve.TABELA_NASTAVITVE);
        db.execSQL(Nastavitve.CREATE_TABLE_NASTAVITVE);
        db.close();
    }

    //posodobitev podatkov v tabelah (Aplikacija ne omogoca posodobitve podatkov)

}
