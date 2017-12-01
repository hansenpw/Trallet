package com.microlabs.trallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.presenter.AddBookActivityPresenter
import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.AddBookActivityView
import kotlinx.android.synthetic.main.activity_add_new_book.*
import kotlinx.android.synthetic.main.content_add_new_book.*
import org.jetbrains.anko.toast

class AddBookActivity : AppCompatActivity(), AddBookActivityView {

    private val presenter: AddBookActivityPresenter by lazy {
        AddBookActivityPresenter(this, DatabaseBookRepository())
    }
    private val id: Int by lazy {
        intent.getIntExtra("id", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_book)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (id != 0) {
            txtEditTitle.setText(intent.getStringExtra("lblTitle"))
            txtEditDescription.setText(intent.getStringExtra("lblDescription"))
        }

        fab.setOnClickListener {
            val title = txtEditTitle.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(this, "Please Input Title", Toast.LENGTH_SHORT).show()
            } else {
                if (id == 0) {
                    presenter.insertNewBook(title, txtEditDescription.text.toString())
                } else {
                    presenter.updateBook(id, title, txtEditDescription.text.toString())
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun showError() {
        toast("Fail to insert book").show()
    }

    override fun done() {
        finish()
    }

    override fun showBook(book: Book) {
        txtEditTitle.setText(book.title)
        txtEditDescription.setText(book.desc)
    }

    override fun onDestroy() {
        presenter.close()
        super.onDestroy()
    }
}
