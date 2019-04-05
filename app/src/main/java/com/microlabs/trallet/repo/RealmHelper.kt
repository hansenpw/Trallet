package com.microlabs.trallet.repo

/**
 * Created by hanse on 11/25/2017.
 *
 * static function for RealmHelper
 */

//internal fun deleteBook(realm: Any, bookId: Int) =
////        realm.executeTransaction {
////            it.where(Book::class.java).equalTo(Book.fId, bookId).findFirst()?.deleteFromRealm()
////            it.where(RExpense::class.java).equalTo(RExpense.fBook, bookId).findAll().deleteAllFromRealm()
////        }
//
//internal fun deleteExpense(realm: Realm, expenseId: Int) =
//        realm.use {
//            it.where(RExpense::class.java).equalTo(RExpense.fId, expenseId).findFirst()?.deleteFromRealm()
//        }
//
//
//fun getCurrencyById(currId: Int): RCurrency =
//        Realm.getDefaultInstance().where(RCurrency::class.java).equalTo(RCurrency.fId, currId).findFirst()!!
//
//fun getCategoryById(catId: Int): RCategory =
//        Realm.getDefaultInstance().where(RCategory::class.java).equalTo(RCategory.fId, catId).findFirst()!!
//
//
//internal fun canCurrencyDelete(realm: Realm, currId: Int): Boolean =
//        realm.where(RExpense::class.java).equalTo(RExpense.fCurrency, currId).findFirst() == null
