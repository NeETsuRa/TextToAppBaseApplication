package com.di.ne.diplomska.SQLite;

/**
 * Razred ki vsebuje vsebino reklam ki nastopajo v Aplikaciji.
 * Ti Podatki so odvisni od verzije in so zapisane v Podatkovni bazi Aplikacije
 */

public class Reklame {
    //Ime Tabele
    public static final String TABELA_REKLAME = "reklame";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_NASLOV="naslov";
    public static final String KEY_VSEBINA="vebina";
    public static final String KEY_URL="url";
    public static final String KEY_SLIKA="slika";
    public static final String[] COLUMNS = {KEY_ID,KEY_NASLOV,KEY_VSEBINA,KEY_URL,KEY_SLIKA};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_REKLAME = "CREATE TABLE " + TABELA_REKLAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"  + KEY_NASLOV + " TEXT,"  + KEY_VSEBINA + " TEXT,"
            + KEY_URL + " TEXT,"  + KEY_SLIKA + " TEXT"
            + ")";

    //Vrednosti
    int ID;
    String naslov;
    String vsebina;
    String URL;
    String slika;

    public Reklame() {
    }

    public Reklame(int ID, String naslov, String vsebina, String URL, String slika) {
        this.naslov = naslov;
        this.vsebina = vsebina;
        this.URL = URL;
        this.slika = slika;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
}
