package com.microlabs.trallet

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.microlabs.trallet.databinding.ActivityAddNewBookBinding
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.viewmodel.AddBookViewModel
import kotlinx.android.synthetic.main.content_add_new_book.*

class AddBookActivity : AppCompatActivity() {

    private val book: Book? by lazy {
        intent.getParcelableExtra("book") as Book?
    }

    private lateinit var viewModel: AddBookViewModel
    private lateinit var binding: ActivityAddNewBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_book)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(AddBookViewModel::class.java)

        if (book != null) {
            binding.content.txtEditTitle.setText(book!!.title)
            binding.content.txtEditDescription.setText(book!!.desc)
        }

        binding.fab.setOnClickListener {
            val title = binding.content.txtEditTitle.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(this, "Please Input Title", Toast.LENGTH_SHORT).show()
            } else {
                if (book == null) {
                    viewModel.insertNewBook(title, txtEditDescription.text.toString())
                } else {
                    viewModel.updateBook(book!!.id, title, txtEditDescription.text.toString())
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
