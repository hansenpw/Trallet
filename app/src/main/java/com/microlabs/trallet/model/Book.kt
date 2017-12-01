package com.microlabs.trallet.model

import io.realm.Realm
import io.realm.RealmObject

/**
 * Book Class
 */
open class Book : RealmObject {
    var id: Int = 0
        private set
    var title: String = ""
    var desc: String = ""

    constructor()

    constructor(id: Int, title: String) {
        this.id = id
        this.title = title
    }

    fun setId(realm: Realm) {
        id = realm.where(Book::class.java).max(Book.fId).toInt() + 1
    }

    companion object {
        val fId = "id"
    }
}
