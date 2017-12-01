package com.microlabs.trallet.repo

import com.microlabs.trallet.model.Book
import com.microlabs.trallet.model.Category
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense
import io.realm.Realm

/**
 * Created by hanse on 11/25/2017.
 *
 * static function for RealmHelper
 */

internal fun deleteBook(realm: Realm, bookId: Int) =
        realm.use {
            it.where(Book::class.java).equalTo(Book.fId, bookId).findFirst().deleteFromRealm()
            it.where(Expense::class.java).equalTo(Expense.fBook, bookId).findAll().deleteAllFromRealm()
        }

internal fun deleteExpense(realm: Realm, expenseId: Int) =
        realm.use {
            it.where(Expense::class.java).equalTo(Expense.fId, expenseId).findFirst().deleteFromRealm()
        }


fun getCurrencyById(currId: Int): Currency =
        Realm.getDefaultInstance().where(Currency::class.java).equalTo(Currency.fId, currId).findFirst()

fun getCategoryById(catId: Int): Category =
        Realm.getDefaultInstance().where(Category::class.java).equalTo(Category.fId, catId).findFirst()


internal fun canCurrencyDelete(realm: Realm, currId: Int): Boolean =
        realm.where(Expense::class.java).equalTo(Expense.fCurrency, currId).findFirst() == null
