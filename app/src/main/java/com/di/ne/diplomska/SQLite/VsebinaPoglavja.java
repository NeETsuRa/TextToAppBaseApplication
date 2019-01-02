package com.di.ne.diplomska.SQLite;

import java.util.List;

/**
 * Created by Nejc on 01.06.2016.
 */
public class VsebinaPoglavja {

    //Ime Tabele
    public static final String TABELA_VSEBINAPOGLAVJA = "vsebinapoglavja";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_OPIS="opis";
    public static final String KEY_SLIKA="slika";
    public static final String KEY_ID_PPOGLAVJA="id_ppoglavja";
    public static final String KEY_ID_ENACBE="id_enacbe";
    public static final String[] COLUMNS = {KEY_ID,KEY_OPIS,KEY_SLIKA,KEY_ID_PPOGLAVJA,KEY_ID_ENACBE};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_VSEBINAPOGLAVJA = "CREATE TABLE " + TABELA_VSEBINAPOGLAVJA + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OPIS + " TEXT," + KEY_SLIKA + " TEXT,"
            + KEY_ID_PPOGLAVJA + " INTEGER," + KEY_ID_ENACBE + " INTEGER"
            + ")";

    //Vrednosti
    long ID;
    String opis;
    String slika;
    long ID_PPoglavja;
    long ID_Enacbe;

    //Konstruktorji
    public VsebinaPoglavja() {
    }
    public VsebinaPoglavja(String opis, String slika) {
        this.opis=opis;
        this.slika=slika;
    }
    public VsebinaPoglavja( String opis, String slika, int ID_PPoglavja, int ID_Enacbe) {
        this.opis=opis;
        this.slika=slika;
        this.ID_PPoglavja=ID_PPoglavja;
        this.ID_Enacbe=ID_Enacbe;
    }
    public VsebinaPoglavja(int ID, String opis, String slika, int ID_PPoglavja, int ID_Enacbe) {
        this.ID=ID;
        this.opis=opis;
        this.slika=slika;
        this.ID_PPoglavja=ID_PPoglavja;
        this.ID_Enacbe=ID_Enacbe;
    }

    //Getter/Setter
    public long getID_Enacbe() {
        return ID_Enacbe;
    }

    public void setID_Enacbe(long ID_Enacbe) {
        this.ID_Enacbe = ID_Enacbe;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public long getID_PPoglavja() {
        return ID_PPoglavja;
    }

    public void setID_PPoglavja(long ID_PPoglavja) {
        this.ID_PPoglavja = ID_PPoglavja;
    }
}
