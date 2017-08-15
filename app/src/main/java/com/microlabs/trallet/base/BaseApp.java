package com.microlabs.trallet.base;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Base App Class for Realm Configuration
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().schemaVersion(1).migration(new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                if (oldVersion == 0) {
                    realm.getSchema().get("Book")
                            .removeField("total");
                }
            }
        }).build();
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
