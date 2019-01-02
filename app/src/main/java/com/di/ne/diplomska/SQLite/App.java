package com.di.ne.diplomska.SQLite;

/**
 * Razred ki vsebuje osnovne informacije o Aplikaciji.
 * Ti Podatki so odvisni od verzije in so zapisane v Podatkovni bazi Aplikacije
 */

public class App {
    //Ime Tabele
    public static final String TABELA_APP = "app";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_VERZIJA="verzija";
    public static final String KEY_IMEAPLIKACIJE="imeAplikacije";
    public static final String[] COLUMNS = {KEY_ID,KEY_VERZIJA,KEY_IMEAPLIKACIJE};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_APP = "CREATE TABLE " + TABELA_APP + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_VERZIJA + " INTEGER," + KEY_IMEAPLIKACIJE + " TEXT"
            + ")";

    //Vrednosti
    int ID;
    long verzija;
    String imeAplikacije;

    public App() {
    }

    public App(long ver, String ime) {
        this.verzija=ver;
        this.imeAplikacije = ime;
    }

    public App(int id, long verzija, String ime) {
        this.ID=id;
        this.verzija=verzija;
        this.imeAplikacije = ime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getVerzija() {
        return verzija;
    }

    public void setVerzija(long verzija) {
        this.verzija = verzija;
    }

    public String getImeAplikacije() {
        return imeAplikacije;
    }

    public void setImeAplikacije(String imeAplikacije) {
        this.imeAplikacije = imeAplikacije;
    }


}
