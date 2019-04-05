package com.microlabs.trallet

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.microlabs.trallet.adapter.BookRVAdapter
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var bookRVAdapter: BookRVAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setup()

        fab.setOnClickListener {
            startActivity<AddBookActivity>()
        }
    }

    private fun setup() {
        rvBook.layoutManager = LinearLayoutManager(this)
        rvBook.itemAnimator = DefaultItemAnimator()
        bookRVAdapter = BookRVAdapter(BookItemListener(this, validateDeleteBook))
        rvBook.adapter = bookRVAdapter

        viewModel.loadBooks().observe(this, Observer {
            if (it.isEmpty()) {
                rvBook.visibility = View.GONE
                txtEmpty.visibility = View.VISIBLE
            } else {
                txtEmpty.visibility = View.GONE
                rvBook.visibility = View.VISIBLE
            }
            info("live: ${it.size}")
            bookRVAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_currency) startActivity<CurrencyActivity>()
        else if (item.itemId == R.id.menu_settings) startActivity<SettingsActivity>()

        return super.onOptionsItemSelected(item)
    }

    /**
     * Validation to Delete Book
     */
    private val validateDeleteBook = fun(book: Book) {
        alert("Are you sure want to delete ${book.title} book?", "Delete Confirmation") {
            yesButton {
                it.dismiss()
                viewModel.deleteBook(book)
            }
            noButton {
                it.dismiss()
            }
        }.show()
    }

    /**
     * Item Click Listener Interface for Book RecyclerView
     */
    class BookItemListener(val context: Context, val validateDeleteBook: (Book) -> Unit) {
        fun onDetailClick(bookId: Int, bookTitle: String) {
            context.startActivity<BookDetailActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }

        fun onDeleteClick(book: Book) {
            validateDeleteBook(book)
        }

        fun onEditClick(bookId: Int, bookTitle: String, bookDesc: String) {
            context.startActivity<AddBookActivity>("id" to bookId, "lblTitle" to bookTitle, "lblDescription" to bookDesc)
        }

        fun onBookClick(bookId: Int, bookTitle: String) {
            context.startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }

        fun onAddExpenseClick(bookId: Int, bookTitle: String) {
            context.startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }
    }
}
