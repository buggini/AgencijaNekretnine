package rs.aleph.android.example25.pripremni.db.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = Nekretnina.TABLE_NAME_USERS)
public class Nekretnina {

    public static final String TABLE_NAME_USERS = "nekretnina";
    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_NEKRETNINA_NAME = "name";
    public static final String TABLE_NEKRETNINA_OPIS = "opis";

    public static final String TABLE_NEKRETNINA_ADRESA = "adresa";
    public static final String TABLE_NEKRETNINA_TELEFON = "telefon";
    public static final String TABLE_NEKRETNINA_KVADRATURA = "kvadratura";
    public static final String TABLE_NEKRETNINA_BROJSOBA = "brojsobe";
    public static final String TABLE_NEKRETNINA_CENA = "cena";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_NEKRETNINA_NAME)
    private String mName;

    @DatabaseField(columnName = TABLE_NEKRETNINA_OPIS)
    private String mOpis;



    @DatabaseField(columnName = TABLE_NEKRETNINA_ADRESA)
    private String mAdresa;

    @DatabaseField(columnName = TABLE_NEKRETNINA_TELEFON)
    private int mTelefon;

    @DatabaseField(columnName = TABLE_NEKRETNINA_KVADRATURA)
    private double mKvadratura;

    @DatabaseField(columnName = TABLE_NEKRETNINA_BROJSOBA)
    private int mBrojSoba;

    @DatabaseField(columnName = TABLE_NEKRETNINA_CENA)
    private double mCena;


    public Nekretnina(){

     //prazan konstruktor je ovde obavezan
    }

    public int getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }


    public String getmAdresa() {
        return mAdresa;
    }

    public void setmAdresa(String mAdresa) {
        this.mAdresa = mAdresa;
    }

    public int getmTelefon() {
        return mTelefon;
    }

    public void setmTelefon(int mTelefon) {
        this.mTelefon = mTelefon;
    }

    public double getmKvadratura() {
        return mKvadratura;
    }

    public void setmKvadratura(double mKvadratura) {
        this.mKvadratura = mKvadratura;
    }

    public int getmBrojSoba() {
        return mBrojSoba;
    }

    public void setmBrojSoba(int mBrojSoba) {
        this.mBrojSoba = mBrojSoba;
    }

    public double getmCena() {
        return mCena;
    }

    public void setmCena(double mCena) {
        this.mCena = mCena;
    }

    @Override
    public String toString() {
        return mName;
    }
}
