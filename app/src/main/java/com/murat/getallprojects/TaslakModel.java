package com.murat.getallprojects;

import java.util.ArrayList;

/**
 * Created by murat on 9.07.2017.
 */

public class TaslakModel extends ArrayList<String> {
    String ozelGun;
    String tarih;
    String dbName;

    public TaslakModel(String gun) {
        this.ozelGun = gun;
    }

    public TaslakModel(String day, String date) {
        this.ozelGun = day;
        this.tarih = date;
     }

    public TaslakModel() {
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getTarih() {
        return tarih;
    }

    public String getOzelGun() {
        return ozelGun;
    }

    public void setOzelGun(String ozelGun) {
        this.ozelGun = ozelGun;
    }


}
