package com.microlabs.trallet.model;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Currency Class
 */
public class Currency extends RealmObject {

    private int id;
    private String name;
    private double value;

    public static final String fId = "id";
    public static final String fName = "name";
    public static final String fValue = "value";

    public int getId() {
        return id;
    }

    public void setId(Realm realm) {
        id = realm.where(Currency.class).max(Currency.fId).intValue() + 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
