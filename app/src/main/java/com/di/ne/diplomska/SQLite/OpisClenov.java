package com.di.ne.diplomska.SQLite;

/**
 * Created by Nejc on 01.06.2016.
 */
public class OpisClenov {
    //Ime Tabele
    public static final String TABELA_OPISCLENOV = "opisclenov";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_CRKA="crka";
    public static final String KEY_POMEN="pomen";
    public static final String KEY_KONSTANTA="konstanta";
    public static final String[] COLUMNS = {KEY_ID,KEY_CRKA,KEY_POMEN,KEY_KONSTANTA};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_OPISCLENOV = "CREATE TABLE " + TABELA_OPISCLENOV + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CRKA + " TEXT," + KEY_POMEN + " TEXT,"
            + KEY_KONSTANTA + " INTEGER"
            + ")";

    //Vrednosti
    int ID;
    String crka;
    String pomen;
    double konstanta;

    public OpisClenov() {
    }
    public OpisClenov(String crka, String pomen, int konstanta) {
        this.crka=crka;
        this.pomen=pomen;
        this.konstanta=konstanta;
    }
    public OpisClenov(int ID, String crka, String pomen, int konstanta) {
        this.ID=ID;
        this.crka=crka;
        this.pomen=pomen;
        this.konstanta=konstanta;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCrka() {
        return crka;
    }

    public void setCrka(String crka) {
        this.crka = crka;
    }

    public String getPomen() {
        return pomen;
    }

    public void setPomen(String pomen) {
        this.pomen = pomen;
    }

    public double getKonstanta() {
        return konstanta;
    }

    public void setKonstanta(double konstanta) {
        this.konstanta = konstanta;
    }
}
