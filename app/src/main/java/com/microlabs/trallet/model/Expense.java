package com.microlabs.trallet.model;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Expense Class
 */
public class Expense extends RealmObject {

    private int id;
    @Required
    private String title;
    private double value;
    private int categoryId;
    private int currencyId;
    private String details;
    private Date date;
    private int bookId;

    public static final String fId= "id";
    public static final String fTitle = "title";
    public static final String fValue = "value";
    public static final String fCategory = "categoryId";
    public static final String fCurrency = "currencyId";
    public static final String fDetails = "details";
    public static final String fDate = "date";
    public static final String fBook = "bookId";

    public int getId() {
        return id;
    }

    public void setId(Realm realm) {
        id = realm.where(Expense.class).max(Expense.fId).intValue() + 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
