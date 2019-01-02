package com.di.ne.diplomska.SQLite;

/**
 * Created by Nejc on 01.06.2016.
 */
public class PPoglavja {
    //Ime Tabele
    public static final String TABELA_PPOGLAVJA = "ppoglavja";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_IMEPOGLAVJA="imePoPoglavja";
    public static final String KEY_OPISPOGLAVJA="opisPoglavja";
    public static final String KEY_ID_GPOGLAVJA="ID_GPoglavja";
    public static final String[] COLUMNS = {KEY_ID,KEY_IMEPOGLAVJA,KEY_OPISPOGLAVJA,KEY_ID_GPOGLAVJA};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_PPOGLAVJA = "CREATE TABLE " + TABELA_PPOGLAVJA + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMEPOGLAVJA + " TEXT," + KEY_OPISPOGLAVJA + " TEXT,"
            + KEY_ID_GPOGLAVJA + " INTEGER"
            + ")";

    //Vrednosti
    long ID;
    String imePoPoglavja;
    String opisPoglavja;
    long ID_GPoglavja;

    public PPoglavja() {
    }
    public PPoglavja( String imePoPoglavja, String opisPoglavja) {
        this.imePoPoglavja=imePoPoglavja;
        this.opisPoglavja=opisPoglavja;
        this.ID_GPoglavja=ID_GPoglavja;
    }
    public PPoglavja(String imePoPoglavja, String opisPoglavja, int ID_GPoglavja) {
        this.imePoPoglavja=imePoPoglavja;
        this.opisPoglavja=opisPoglavja;
        this.ID_GPoglavja=ID_GPoglavja;
    }
    public PPoglavja(int ID, String imePoPoglavja, String opisPoglavja, int ID_GPoglavja) {
        this.ID=ID;
        this.imePoPoglavja=imePoPoglavja;
        this.opisPoglavja=opisPoglavja;
        this.ID_GPoglavja=ID_GPoglavja;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getImePoPoglavja() {
        return imePoPoglavja;
    }

    public void setImePoPoglavja(String imePoPoglavja) {
        this.imePoPoglavja = imePoPoglavja;
    }

    public String getOpisPoglavja() {
        return opisPoglavja;
    }

    public void setOpisPoglavja(String opisPoglavja) {
        this.opisPoglavja = opisPoglavja;
    }

    public long getID_GPoglavja() {
        return ID_GPoglavja;
    }

    public void setID_GPoglavja(long ID_GPoglavja) {
        this.ID_GPoglavja = ID_GPoglavja;
    }
}
