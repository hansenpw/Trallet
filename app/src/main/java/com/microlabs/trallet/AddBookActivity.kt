package com.microlabs.trallet

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.microlabs.trallet.viewmodel.AddBookViewModel
import kotlinx.android.synthetic.main.activity_add_new_book.*
import kotlinx.android.synthetic.main.content_add_new_book.*

class AddBookActivity : AppCompatActivity() {

    private val id: Int by lazy {
        intent.getIntExtra("id", -1)
    }

    private lateinit var viewModel: AddBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_book)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(AddBookViewModel::class.java)

        if (id != -1) {
            txtEditTitle.setText(intent.getStringExtra("lblTitle"))
            txtEditDescription.setText(intent.getStringExtra("lblDescription"))
        }

        fab.setOnClickListener {
            val title = txtEditTitle.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(this, "Please Input Title", Toast.LENGTH_SHORT).show()
            } else {
                if (id == -1) {
                    viewModel.insertNewBook(title, txtEditDescription.text.toString())
                } else {
                    viewModel.updateBook(id, title, txtEditDescription.text.toString())
                }
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}