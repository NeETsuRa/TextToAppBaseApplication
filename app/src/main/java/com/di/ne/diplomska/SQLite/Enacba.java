package com.di.ne.diplomska.SQLite;

/**
 * Created by Nejc on 01.06.2016.
 */
public class Enacba {
    //Ime Tabele
    public static final String TABELA_ENACBA = "enacba";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_IME="ime";
    public static final String KEY_ENACBA="enacba";
    public static final String[] COLUMNS = {KEY_ID,KEY_IME,KEY_ENACBA};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_ENACBA = "CREATE TABLE " + TABELA_ENACBA + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IME + " TEXT," + KEY_ENACBA + " TEXT"
            + ")";

    //Vrednosti
    int ID;
    String ime;
    String enacba;

    public Enacba() {
    }

    public Enacba(String ime, String enacba) {
        this.ime=ime;
        this.enacba=enacba;
    }

    public Enacba(int ID, String ime, String enacba) {
        this.ID=ID;
        this.ime=ime;
        this.enacba=enacba;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEnacba() {
        return enacba;
    }

    public void setEnacba(String enacba) {
        this.enacba = enacba;
    }
}
