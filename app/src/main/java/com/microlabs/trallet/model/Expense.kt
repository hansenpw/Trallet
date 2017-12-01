package com.microlabs.trallet.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.Required
import java.util.*

/**
 * Expense Class
 */
open class Expense : RealmObject() {

    var id: Int = 0
        private set
    @Required
    var title: String = ""
    var value: Double = 0.0
    var categoryId: Int = 0
    var currencyId: Int = 0
    var details: String = ""
    var date: Date = Date()
    var bookId: Int = 0

    fun setId(realm: Realm) {
        id = realm.where(Expense::class.java).max(Expense.fId).toInt() + 1
    }

    companion object {

        val fId = "id"
        val fValue = "value"
        val fCategory = "categoryId"
        val fCurrency = "currencyId"
        val fBook = "bookId"
    }
}
