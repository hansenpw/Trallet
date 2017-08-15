package com.microlabs.trallet.repo;

import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.model.Category;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.model.Expense;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by Hansen on 4/2/2017.
 * <p>
 * Database of Book and Expense Repository
 */

public class DatabaseBookRepository {

    private Realm realm;

    /**
     * Get Default Instance of Realm Database
     */
    public DatabaseBookRepository() {
        realm = Realm.getDefaultInstance();
    }

    /**
     * Get All Book List sorted by its Id
     *
     * @return list of Book, sort by its Id ascending
     */
    public List<Book> getBookList() {
        return realm.where(Book.class).findAllSorted(Book.fId);
    }

    /**
     * Check whether default Category is exist
     * Default Category is 4 (Food, Transport, Shop, and Others)
     *
     * @return true if default Category is NOT exists
     */
    public boolean isDefaultCategoryNotExist() {
        return realm.where(Category.class).count() < 4;
    }

    /**
     * Check whether default Currency is exist
     * Default Currency is 2 (USD $ and IDR Rp)
     *
     * @return true if Default Currency is NOT exists
     */
    public boolean isDefaultCurrencyNotExist() {
        return realm.where(Currency.class).count() < 2;
    }

    /**
     * Insert Default Category to Repository if not exists
     */
    public void loadDefaultCategory() {
        for (int i = 0; i < 4; i++) {
            realm.beginTransaction();
            Category c = realm.createObject(Category.class);
            c.setId(realm);
            switch (i) {
                case 0:
                    c.setName("Foods");
                    break;
                case 1:
                    c.setName("Transport");
                    break;
                case 2:
                    c.setName("Shop");
                    break;
                case 3:
                    c.setName("Others");
                    break;
            }
            realm.commitTransaction();
        }
    }

    /**
     * Insert Default Currency if not exists
     */
    public void loadDefaultCurrency() {
        for (int i = 0; i < 2; i++) {
            realm.beginTransaction();
            Currency c = realm.createObject(Currency.class);
            c.setId(realm);
            switch (i) {
                case 0:
                    c.setName("IDR Rp");
                    c.setValue(1.0);
                    break;
                case 1:
                    c.setName("USD $");
                    c.setValue(13500.0);
                    break;
            }
            realm.commitTransaction();
        }
    }

    /**
     * Get List of Expenses from a book
     *
     * @param bookId = bookId where List of Expenses is
     * @return = List of Expenses, sorted by its Id descending (latest is first)
     */
    public List<Expense> getExpenseList(int bookId) {
        return realm.where(Expense.class).equalTo(Expense.fBook, bookId).findAllSorted(Expense.fId, Sort.DESCENDING);
    }

    /**
     * Delete a Book by its Id
     *
     * @param bookId = bookId of Book to remove
     */
    public void deleteBook(int bookId) {
        RealmHelper.deleteBook(realm, bookId);
    }

    /**
     * Delete an Expense by its Id
     *
     * @param bookId    = bookId of Expense to remove
     *                  Used to decrease book total value
     * @param expenseId = expenseId of Expense to remove
     */
    public void deleteExpense(int bookId, int expenseId) {
//        RealmHelper.setBookDelete(realm, bookId, expenseId);
        RealmHelper.deleteExpense(realm, expenseId);
    }

    /**
     * Get Expense Object by its Id
     *
     * @param id = expenseId to get
     * @return = expense object found
     */
    public Expense getExpense(int id) {
        return realm.where(Expense.class).equalTo(Expense.fId, id).findFirst();
    }

    /**
     * Save Expense to database
     * and set its Book total value
     *
     * @param title      = Expense title
     * @param bookId     = Expense bookId
     * @param value      = Expense value
     * @param categoryId = Expense CategoryId
     * @param currencyId = Expense CurrencyId
     * @param date       = Expense Date
     * @param details    = Expense Details
     */
    public void saveExpense(String title, int bookId, Double value, int categoryId, int currencyId, Date date, String details) {
        realm.beginTransaction();
        Expense expense = realm.createObject(Expense.class);
        expense.setId(realm);
        expense.setTitle(title);
        expense.setValue(value);
        expense.setCategoryId(categoryId);
        expense.setCurrencyId(currencyId);
        expense.setDetails(details);
        expense.setBookId(bookId);
        expense.setDate(date);
        realm.commitTransaction();
//        RealmHelper.setBookTotal(realm, bookId, value);
    }

    /**
     * Close Realm Instance to prevent memory leaks
     */
    public void close() {
        realm.close();
    }

    /**
     * Get All Category
     *
     * @return = List of Category
     */
    public List<Category> getCategoryList() {
        return realm.where(Category.class).findAll();
    }

    /**
     * Get All Currency
     *
     * @return = List of Currency
     */
    public List<Currency> getCurrencyList() {
        return realm.where(Currency.class).findAll();
    }

    /**
     * Update Expense Data
     *
     * @param expenseId  = expenseId to be updated
     * @param title      = updated expense title
     * @param bookId     = expense bookId to update the book total value
     * @param value      = updated expense value
     * @param categoryId = updated expense categoryId
     * @param currencyId = updated expense currencyId
     * @param date       = updated expense date
     * @param details    = updated expense details
     * @param oldValue   = expense old value to update the book total value
     */
    public void updateExpense(int expenseId, String title, int bookId, double value, int categoryId, int currencyId, Date date, String details, double oldValue) {
        realm.beginTransaction();
        final Expense expense = realm.where(Expense.class).equalTo(Expense.fId, expenseId).findFirst();
        expense.setTitle(title);
        expense.setValue(value);
        expense.setDetails(details);
        expense.setCategoryId(categoryId);
        expense.setCurrencyId(currencyId);
        expense.setDate(date);
        realm.commitTransaction();
//        RealmHelper.setBookEdit(realm, bookId, oldValue, value);
    }

    /**
     * Save New Currency
     *
     * @param title = Currency title
     * @param value = Currency value
     * @return = true if success to save, false if currency title already exists
     */
    public boolean saveCurrency(String title, double value) {
        if (realm.where(Currency.class).equalTo(Currency.fName, title).findFirst() == null) {
            realm.beginTransaction();
            Currency currency = realm.createObject(Currency.class);
            currency.setId(realm);
            currency.setName(title);
            currency.setValue(value);
            realm.commitTransaction();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update Currency Details
     *
     * @param id    = currencyId of Currency to be updated
     * @param title = currency title
     * @param value = currency value
     * @return true if success to update currency, fail if other currency with same title already exists
     */
    public boolean updateCurrency(int id, String title, double value) {
        if (realm.where(Currency.class).equalTo(Currency.fName, title).notEqualTo(Currency.fId, id).findFirst() != null) {
            realm.beginTransaction();
            Currency currency = realm.where(Currency.class).equalTo(Currency.fId, id).findFirst();
            currency.setName(title);
            currency.setValue(value);
            realm.commitTransaction();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if currency can be removed
     * currency can only be removed when no longer being used
     *
     * @param id = currencyId to be checked
     * @return true if currency can be removed, false otherwise
     */
    public boolean canCurrencyDelete(int id) {
        return RealmHelper.canCurrencyDelete(realm, id);
    }

    public void deleteCurrency(int id) {
        realm.beginTransaction();
        realm.where(Currency.class).equalTo(Currency.fId, id).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    public void deleteCurrencyNew(int id) {
        if (realm.isInTransaction()) {
            commitTransaction();
        }
        realm.beginTransaction();
        realm.where(Currency.class).equalTo(Currency.fId, id).findFirst().deleteFromRealm();
    }

    public boolean commitTransaction() {
        if (realm.isInTransaction()) {
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public void cancelTransaction() {
        realm.cancelTransaction();
    }

    /**
     * Save new Book
     *
     * @param title = book title
     * @param desc  = book description (optional)
     */
    public void saveBook(String title, String desc) {
        realm.beginTransaction();
        Book book = realm.createObject(Book.class);
        book.setId(realm);
        book.setTitle(title);
        book.setDesc(desc);
//        book.setTotal(0.0);
        realm.commitTransaction();
    }

    /**
     * get book details by its id
     *
     * @param id = bookId
     * @return = Book
     */
    public Book getBook(int id) {
        return realm.where(Book.class).equalTo(Book.fId, id).findFirst();
    }

    /**
     * Update Book Details
     *
     * @param id    = bookId of Book to be updated
     * @param title = book title
     * @param desc  = book description (optional)
     */
    public void updateBook(int id, String title, String desc) {
        realm.beginTransaction();
        Book book = realm.where(Book.class).equalTo(Book.fId, id).findFirst();
        book.setTitle(title);
        book.setDesc(desc);
        realm.commitTransaction();
    }
}
