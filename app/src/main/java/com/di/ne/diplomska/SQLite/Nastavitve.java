package com.di.ne.diplomska.SQLite;

/**
 * Razred ki vsebuje nastavitve Aplikacijie.
 * Ti Podatki so odvisni od verzije in so zapisane v Podatkovni bazi Aplikacije
 */
public class Nastavitve {

    //Ime Tabele
    public static final String TABELA_NASTAVITVE = "nastavitve";
    //Imena Stolpcev
    public static final String KEY_ID = "id";
    public static final String KEY_VELIKOSTPISAVE="velikostPisave";
    public static final String KEY_BARVA="barva";
    public static final String KEY_FILEPATH="filepath";
    public static final String KEY_IMAGEPATH="imagepath";
    public static final String KEY_ENACBEPATH="enacbepath";
    public static final String[] COLUMNS = {KEY_ID,KEY_VELIKOSTPISAVE,KEY_BARVA};
    //Klic za ustvarjanje Tabele
    public static final String CREATE_TABLE_NASTAVITVE = "CREATE TABLE " + TABELA_NASTAVITVE + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_VELIKOSTPISAVE + " INTEGER," + KEY_BARVA + " TEXT,"
            + KEY_FILEPATH + " TEXT," + KEY_IMAGEPATH + " TEXT," + KEY_ENACBEPATH + " TEXT"
            + ")";

    //Vrednosti
    int ID;
    long velikostPisave;
    String barva;
    String filePath;
    String imagePath;
    String enacbePath;

    public Nastavitve() {
    }

    public Nastavitve(int ID, long velikostPisave) {
        this.velikostPisave = velikostPisave;
    }

    public Nastavitve(int ID, String barva) {
        this.barva = barva;
    }

    public Nastavitve(int ID, long velikostPisave, String barva) {
        this.velikostPisave = velikostPisave;
        this.barva = barva;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getVelikostPisave() {
        return velikostPisave;
    }

    public void setVelikostPisave(long velikostPisave) {
        this.velikostPisave = velikostPisave;
    }

    public String getBarva() {
        return barva;
    }

    public void setBarva(String barva) {
        this.barva = barva;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEnacbePath() {
        return enacbePath;
    }

    public void setEnacbePath(String enacbePath) {
        this.enacbePath = enacbePath;
    }
}
