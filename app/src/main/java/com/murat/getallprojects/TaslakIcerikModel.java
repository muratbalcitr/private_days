package com.murat.getallprojects;

/**
 * Created by murat on 24.06.2017.
 */

public class TaslakIcerikModel {
    String key, value;

    public TaslakIcerikModel() {
    }
    public TaslakIcerikModel(String sozKey, String sozValue) {
        this.key = sozKey;
        this.value = sozValue;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }


}