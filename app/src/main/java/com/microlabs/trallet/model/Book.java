package com.microlabs.trallet.model;

import com.microlabs.trallet.repo.RealmHelper;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Book Class
 */
public class Book extends RealmObject {

    public static final String fId= "id";
    public static final String fTitle = "title";
    public static final String fDesc = "desc";
    public static final String fTotal = "total";
    private int id;
    private String title;
    private String desc;
    private double total;

    public Book() {
    }

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(Realm realm) {
        id = realm.where(Book.class).max(Book.fId).intValue() + 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setTotal() {
        total = RealmHelper.getBookTotal(id);
    }

    public void setTotal(Realm realm) {
        total = RealmHelper.getBookTotal(realm, id);
    }
}
