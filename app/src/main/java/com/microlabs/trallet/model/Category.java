package com.microlabs.trallet.model;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Category Class
 */
public class Category extends RealmObject {

    private int id;
    private String name;

    public static final String fId = "id";
    public static final String fName = "name";

    public int getId() {
        return id;
    }

    public void setId(Realm realm) {
        id = realm.where(Category.class).max(Category.fId).intValue() + 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
