package com.microlabs.trallet.repo

/**
 * Created by Hansen on 4/2/2017.
 *
 *
 * Database of Book and RExpense Repository
 */

open class DatabaseBookRepository {

////    private val realm: Realm = Realm.getDefaultInstance()
//
//    /**
//     * Get All Book List sorted by its Id
//     *
//     * @return list of Book, sort by its Id ascending
//     */
////    open val bookList: RealmResults<Book>? = null
////        get() = realm.where(Book::class.java).sort(Book.fId).findAllAsync()
//
//    /**
//     * Check whether default RCategory is exist
//     * Default RCategory is 4 (Food, Transport, Shop, and Others)
//     *
//     * @return true if default RCategory is NOT exists
//     */
//    open val isDefaultCategoryNotExist: Boolean
//        get() = realm.where(RCategory::class.java).count() < 4
//
//    /**
//     * Check whether default RCurrency is exist
//     * Default RCurrency is 2 (USD $ and IDR Rp)
//     *
//     * @return true if Default RCurrency is NOT exists
//     */
//    open val isDefaultCurrencyNotExist: Boolean
//        get() = realm.where(RCurrency::class.java).count() < 2
//
//    /**
//     * Get All RCategory
//     *
//     * @return = List of RCategory
//     */
//    open val RCategoryList: List<RCategory>
//        get() = realm.where(RCategory::class.java).findAll()
//
//    /**
//     * Get All RCurrency
//     *
//     * @return = List of RCurrency
//     */
//    open val RCurrencyList: List<RCurrency>
//        get() = realm.where(RCurrency::class.java).findAll()
//
//    /**
//     * Insert Default RCategory to Repository if not exists
//     */
//    open fun loadDefaultCategory() {
//        for (i in 0..3) {
//            realm.beginTransaction()
//            val c = realm.createObject(RCategory::class.java)
//            c.setId(realm)
//            when (i) {
//                0 -> c.name = "Foods"
//                1 -> c.name = "Transport"
//                2 -> c.name = "Shop"
//                3 -> c.name = "Others"
//            }
//            realm.commitTransaction()
//        }
//    }
//
//    /**
//     * Insert Default RCurrency if not exists
//     */
//    open fun loadDefaultCurrency() {
//        for (i in 0..1) {
//            realm.beginTransaction()
//            val c = realm.createObject(RCurrency::class.java)
//            c.setId(realm)
//            when (i) {
//                0 -> {
//                    c.name = "IDR Rp"
//                    c.value = 1.0
//                }
//                1 -> {
//                    c.name = "USD $"
//                    c.value = 13500.0
//                }
//            }
//            realm.commitTransaction()
//        }
//    }
//
//    /**
//     * Get List of Expenses from a book
//     *
//     * @param bookId = bookId where List of Expenses is
//     * @return = List of Expenses, sorted by its Id descending (latest is first)
//     */
//    open fun getExpenseList(bookId: Int): List<RExpense> {
//        return realm.where(RExpense::class.java).equalTo(RExpense.fBook, bookId).sort(RExpense.fId, Sort.DESCENDING).findAll()
//    }
//
//    /**
//     * Delete a Book by its Id
//     *
//     * @param bookId = bookId of Book to remove
//     */
//    open fun deleteBook(bookId: Int) {
//        deleteBook(realm, bookId)
//    }
//
//    /**
//     * Delete an RExpense by its Id
//     *
//     * @param bookId    = bookId of RExpense to remove
//     * Used to decrease book total value
//     * @param expenseId = expenseId of RExpense to remove
//     */
//    open fun deleteExpense(bookId: Int, expenseId: Int) {
//        //        RealmHelperKt.setBookDelete(realm, bookId, expenseId);
//        deleteExpense(realm, expenseId)
//    }
//
//    /**
//     * Get RExpense Object by its Id
//     *
//     * @param id = expenseId to get
//     * @return = expense object found
//     */
//    open fun getExpense(id: Int): RExpense {
//        return realm.where(RExpense::class.java).equalTo(RExpense.fId, id).findFirst()!!
//    }
//
//    /**
//     * Save RExpense to database
//     * and set its Book total value
//     *
//     * @param title      = RExpense title
//     * @param bookId     = RExpense bookId
//     * @param value      = RExpense value
//     * @param categoryId = RExpense CategoryId
//     * @param currencyId = RExpense CurrencyId
//     * @param date       = RExpense Date
//     * @param details    = RExpense Details
//     */
//    open fun saveExpense(title: String, bookId: Int, value: Double, categoryId: Int, currencyId: Int, date: Date, details: String) {
//        realm.beginTransaction()
//        val expense = realm.createObject(RExpense::class.java)
//        expense.setId(realm)
//        expense.title = title
//        expense.value = value
//        expense.categoryId = categoryId
//        expense.currencyId = currencyId
//        expense.details = details
//        expense.bookId = bookId
//        expense.date = date
//        realm.commitTransaction()
//        //        RealmHelperKt.setBookTotal(realm, bookId, value);
//    }
//
//    /**
//     * Close Realm Instance to prevent memory leaks
//     */
//    open fun close() {
//        realm.close()
//    }
//
//    /**
//     * Update RExpense Data
//     *
//     * @param expenseId  = expenseId to be updated
//     * @param title      = updated expense title
//     * @param bookId     = expense bookId to update the book total value
//     * @param value      = updated expense value
//     * @param categoryId = updated expense categoryId
//     * @param currencyId = updated expense currencyId
//     * @param date       = updated expense date
//     * @param details    = updated expense details
//     * @param oldValue   = expense old value to update the book total value
//     */
//    open fun updateExpense(expenseId: Int, title: String, bookId: Int, value: Double, categoryId: Int, currencyId: Int, date: Date, details: String, oldValue: Double) {
//        realm.beginTransaction()
//        val expense = realm.where(RExpense::class.java).equalTo(RExpense.fId, expenseId).findFirst()
//        expense?.title = title
//        expense?.value = value
//        expense?.details = details
//        expense?.categoryId = categoryId
//        expense?.currencyId = currencyId
//        expense?.date = date
//        realm.commitTransaction()
//        //        RealmHelperKt.setBookEdit(realm, bookId, oldValue, value);
//    }
//
//    /**
//     * Save New RCurrency
//     *
//     * @param title = RCurrency title
//     * @param value = RCurrency value
//     * @return = true if success to save, false if currency title already exists
//     */
//    open fun saveCurrency(title: String, value: Double): Boolean {
//        if (realm.where(RCurrency::class.java).equalTo(RCurrency.fName, title).findFirst() == null) {
//            realm.beginTransaction()
//            val currency = realm.createObject(RCurrency::class.java)
//            currency.setId(realm)
//            currency.name = title
//            currency.value = value
//            realm.commitTransaction()
//            return true
//        } else {
//            return false
//        }
//    }
//
//    /**
//     * Update RCurrency Details
//     *
//     * @param id    = currencyId of RCurrency to be updated
//     * @param title = currency title
//     * @param value = currency value
//     * @return true if success to update currency, fail if other currency with same title already exists
//     */
//    open fun updateCurrency(id: Int, title: String, value: Double): Boolean {
//        if (realm.where(RCurrency::class.java).equalTo(RCurrency.fName, title).notEqualTo(RCurrency.fId, id).findFirst() != null) {
//            realm.beginTransaction()
//            val currency = realm.where(RCurrency::class.java).equalTo(RCurrency.fId, id).findFirst()
//            currency?.name = title
//            currency?.value = value
//            realm.commitTransaction()
//            return true
//        } else {
//            return false
//        }
//    }
//
//    /**
//     * Check if currency can be removed
//     * currency can only be removed when no longer being used
//     *
//     * @param id = currencyId to be checked
//     * @return true if currency can be removed, false otherwise
//     */
//    open fun canCurrencyDelete(id: Int): Boolean {
//        return canCurrencyDelete(realm, id)
//    }
//
//    open fun deleteCurrency(id: Int) {
////        realm.beginTransaction()
////        realm.where(RCurrency::class.java).equalTo(RCurrency.fId, id).findFirst()?.deleteFromRealm()
////        realm.commitTransaction()
//    }
//
//    open fun deleteCurrencyNew(id: Int) {
//        if (realm.isInTransaction) {
//            commitTransaction()
//        }
//        realm.beginTransaction()
//        realm.where(RCurrency::class.java).equalTo(RCurrency.fId, id).findFirst()?.deleteFromRealm()
//    }
//
//    open fun commitTransaction(): Boolean {
//        if (realm.isInTransaction) {
//            realm.commitTransaction()
//            return true
//        }
//        return false
//    }
//
//    open fun cancelTransaction() {
//        realm.cancelTransaction()
//    }
//
//    /**
//     * Save new Book
//     *
//     * @param title = book title
//     * @param desc  = book description (optional)
//     */
//    open fun saveBook(title: String, desc: String) {
//        realm.beginTransaction()
////        val book = realm.createObject(Book::class.java)
////        book.setId(realm)
////        book.title = title
////        book.desc = desc
//        //        book.setTotal(0.0);
//        realm.commitTransaction()
//    }
//
//    /**
//     * get book details by its id
//     *
//     * @param id = bookId
//     * @return = Book
//     */
//    open fun getBook(id: Int): Book? {
////        return realm.where(Book::class.java).equalTo(Book.fId, id).findFirst()!!
//        return null
//    }
//
//    /**
//     * Update Book Details
//     *
//     * @param id    = bookId of Book to be updated
//     * @param title = book title
//     * @param desc  = book description (optional)
//     */
//    open fun updateBook(id: Int, title: String, desc: String) {
//        realm.beginTransaction()
////        val book = realm.where(Book::class.java).equalTo(Book.fId, id).findFirst()
////        book?.title = title
////        book?.desc = desc
//        realm.commitTransaction()
//    }
}
