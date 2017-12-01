package com.microlabs.trallet.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Base App Class for Realm Configuration
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        Fabric.with(this, Crashlytics())
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .schemaVersion(1).migration { realm, oldVersion, _ ->
            if (oldVersion == 0L) {
                realm.schema.get("Book")
                        .removeField("total")
            }
        }.build())

        /*
        untuk menghapus seluruh isi database Realm
         */
        /*val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.deleteRealm(config)*/
    }
}
