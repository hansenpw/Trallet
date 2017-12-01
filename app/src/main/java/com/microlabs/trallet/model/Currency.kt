package com.microlabs.trallet.model

import io.realm.Realm
import io.realm.RealmObject

/**
 * Currency Class
 */
open class Currency : RealmObject() {

    var id: Int = 0
        private set
    var name: String = ""
    var value: Double = 0.0

    fun setId(realm: Realm) {
        id = realm.where(Currency::class.java).max(Currency.fId).toInt() + 1
    }

    override fun toString(): String {
        return id.toString()
    }

    companion object {
        val fId = "id"
        val fName = "name"
    }
}
