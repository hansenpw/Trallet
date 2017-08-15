package com.microlabs.trallet.base;

import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.model.Category;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.model.Expense;

import io.realm.Realm;

/**
 * Realm Helper Class for Realm tasks
 */
public class RealmHelper {

    public static void deleteBook(Realm realm, int bookId) {
        realm.beginTransaction();
        realm.where(Book.class).equalTo(Book.fId, bookId).findFirst().deleteFromRealm();
        realm.commitTransaction();
        realm.beginTransaction();
        realm.where(Expense.class).equalTo(Expense.fBook, bookId).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void deleteExpense(int expenseId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Expense.class).equalTo(Expense.fId, expenseId).findFirst().deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    public static double getBookTotal(int bookId) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Expense.class).equalTo(Expense.fBook, bookId).sum(Expense.fValue).doubleValue();
    }

    public static double getBookTotal(Realm realm, int bookId) {
        return realm.where(Expense.class).equalTo(Expense.fBook, bookId).sum(Expense.fValue).doubleValue();
    }

    public static void setBookTotal(Realm realm, int bookId, double value) {
        realm.beginTransaction();
        Book temp = realm.where(Book.class).equalTo(Book.fId, bookId).findFirst();
        temp.setTotal(temp.getTotal() + value);
        realm.commitTransaction();
    }

    public static void setBookDelete(Realm realm, int bookId, double value) {
        realm.beginTransaction();
        Book temp = realm.where(Book.class).equalTo(Book.fId, bookId).findFirst();
        temp.setTotal(temp.getTotal() - value);
        realm.commitTransaction();
    }

    public static void setBookEdit(Realm realm, int bookId, double oldValue, double newValue) {
        realm.beginTransaction();
        Book temp = realm.where(Book.class).equalTo(Book.fId, bookId).findFirst();
        temp.setTotal(temp.getTotal() - oldValue + newValue);
        realm.commitTransaction();
    }

    public static Currency getCurrencyById(int currId) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Currency.class).equalTo(Currency.fId, currId).findFirst();

    }

    public static Category getCategoryById(int catId) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Category.class).equalTo(Category.fId, catId).findFirst();
    }

    public static boolean canCurrencyDelete(Realm realm, int currId) {
        return realm.where(Expense.class).equalTo(Expense.fCurrency, currId).findFirst() == null;
    }
}
