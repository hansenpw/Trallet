package com.microlabs.trallet.base;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Base App Class for Realm Configuration
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        /*
        untuk menghapus seluruh isi database Realm
         */
        /*RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(config);*/
    }
}
