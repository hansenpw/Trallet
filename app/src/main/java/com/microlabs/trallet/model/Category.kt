package com.microlabs.trallet.model

import io.realm.Realm
import io.realm.RealmObject

/**
 * Category Class
 */
open class Category : RealmObject() {

    var id: Int = 0
        private set
    var name: String = ""

    fun setId(realm: Realm) {
        id = realm.where(Category::class.java).max(Category.fId).toInt() + 1
    }

    companion object {
        val fId = "id"
    }
}
