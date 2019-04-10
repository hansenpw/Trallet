package com.microlabs.trallet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.microlabs.trallet.databinding.ActivitySettingsBinding
import org.jetbrains.anko.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.content.btnDeleteAll.setOnClickListener {
            alert("Are you sure want to delete all data?", "Delete all data?") {
                yesButton {
//                    Realm.getDefaultInstance().use {
//                        it.deleteAll()
//                    }
                    toast("All data deleted").show()
                    finish()
                }
                noButton {
                    it.dismiss()
                }
            }.show()
        }

        binding.content.btnAbout.setOnClickListener {
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
