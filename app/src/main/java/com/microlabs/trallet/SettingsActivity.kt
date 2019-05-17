package com.microlabs.trallet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.microlabs.trallet.databinding.ActivitySettingsBinding
import com.microlabs.trallet.repo.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
                    GlobalScope.launch(Dispatchers.IO) {
                        AppDatabase.getInstance(applicationContext).bookDao().deleteAllBooks()
                    }
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

        binding.content.rbGroup.check(when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> R.id.rbSystem
            AppCompatDelegate.MODE_NIGHT_NO -> R.id.rbLight
            AppCompatDelegate.MODE_NIGHT_YES -> R.id.rbDark
            else -> R.id.rbSystem
        })

        binding.content.rbGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbSystem -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                R.id.rbDark -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                R.id.rbLight -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else -> {
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
