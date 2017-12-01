package com.microlabs.trallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.content_settings.*
import org.jetbrains.anko.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnDeleteAll.setOnClickListener {
            alert("Are you sure want to delete all data?", "Delete all data?") {
                yesButton {
                    Realm.getDefaultInstance().use {
                        it.deleteAll()
                    }
                    toast("All data deleted").show()
                    finish()
                }
                noButton {
                    it.dismiss()
                }
            }.show()
        }

        btnAbout.setOnClickListener {
            alert("Made by MicroLabs Developers\n" +
                    "AJ\n" +
                    "NA\n" +
                    "NH\n" +
                    "HP", "About Us") {
                okButton {
                    it.dismiss()
                }
            }.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
