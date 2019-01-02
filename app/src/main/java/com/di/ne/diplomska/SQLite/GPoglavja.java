package com.di.ne.diplomska.SQLite;

/**
 * Razred ki opisuje Tabelo Glavnih poglavij v aplikaciji
 */
public class GPoglavja {
    //Ime Tabele
    public static final String TABELA_GLPOGLAVJA = "glpoglavja";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_IMEGLPOGLAVJA="imeGlPoglavja";
    public static final String KEY_OPISPOGLAVJA="opisPoglavja";
    public static final String[] COLUMNS = {KEY_ID,KEY_IMEGLPOGLAVJA,KEY_OPISPOGLAVJA};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_GLPOGLAVJA = "CREATE TABLE " + TABELA_GLPOGLAVJA + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMEGLPOGLAVJA + " TEXT," + KEY_OPISPOGLAVJA + " TEXT"
            + ")";

    //Vrednosti
    long ID;
    String imeGlPoglavja;
    String opisPoglavja;

    public GPoglavja() {
    }

    public GPoglavja(String imeGlPoglavja, String opisPoglavja) {
        this.imeGlPoglavja=imeGlPoglavja;
        this.opisPoglavja=opisPoglavja;
    }

    public GPoglavja(int ID, String imeGlPoglavja, String opisPoglavja) {
        this.ID=ID;
        this.imeGlPoglavja=imeGlPoglavja;
        this.opisPoglavja=opisPoglavja;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getImeGlPoglavja() {
        return imeGlPoglavja;
    }

    public void setImeGlPoglavja(String imeGlPoglavja) {
        this.imeGlPoglavja = imeGlPoglavja;
    }

    public String getOpisPoglavja() {
        return opisPoglavja;
    }

    public void setOpisPoglavja(String opisPoglavja) {
        this.opisPoglavja = opisPoglavja;
    }


}
