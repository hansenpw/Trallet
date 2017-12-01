package com.microlabs.trallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.microlabs.trallet.adapter.BookRVAdapter
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.presenter.MainActivityPresenter
import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), MainActivityView {

    private lateinit var bookRVAdapter: BookRVAdapter
    private val presenter: MainActivityPresenter by lazy { MainActivityPresenter(this, DatabaseBookRepository()) }

    /**
     * Item Click Listener for Book RecyclerView
     */
    private val itemListener = object : BookItemListener {
        override fun onDetailClick(bookId: Int, bookTitle: String) {
            startActivity<BookDetailActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }

        override fun onDeleteClick(bookId: Int, bookTitle: String) {
            validateDeleteBook(bookId, bookTitle)
        }

        override fun onEditClick(bookId: Int, bookTitle: String, bookDesc: String) {
            startActivity<AddBookActivity>("id" to bookId, "lblTitle" to bookTitle, "lblDescription" to bookDesc)
        }

        override fun onBookClick(bookId: Int, bookTitle: String) {
            startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }

        override fun onAddExpenseClick(bookId: Int, bookTitle: String) {
            startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setup()

        fab.setOnClickListener {
            presenter.addNewBook()
        }
    }

    private fun setup() {
        rvBook.layoutManager = LinearLayoutManager(this)
        rvBook.itemAnimator = DefaultItemAnimator()
        bookRVAdapter = BookRVAdapter(itemListener)
        rvBook.adapter = bookRVAdapter
    }

    override fun onResume() {
        super.onResume()
        updateAdapterList()
    }

    /**
     * Update Data though Presenter
     */
    fun updateAdapterList() {
        // Update list of books sorted by its ID
        presenter.loadBooks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_currency) presenter.toCurrency()
        else if (item.itemId == R.id.menu_settings) presenter.toSettings()

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        presenter.checkDefault()
    }

    override fun onDestroy() {
        // Close Realm Instance when Activity is Destroyed to prevent memory leaks
        presenter.close()
        super.onDestroy()
    }

    /**
     * Validation to Delete Book
     *
     * @param id    = bookId to remove
     * @param title = bookTitle to remove
     */
    fun validateDeleteBook(id: Int, title: String) {
        alert("Are you sure want to delete $title book?", "Delete Confirmation") {
            yesButton {
                it.dismiss()
                presenter.deleteBook(id)
                updateAdapterList()
            }
            noButton {
                it.dismiss()
            }
        }.show()
    }

    override fun showBooks(books: List<Book>) {
        txtEmpty.visibility = View.GONE
        rvBook.visibility = View.VISIBLE
        bookRVAdapter.updateList(books)
    }

    override fun showNoBook(books: List<Book>) {
        rvBook.visibility = View.GONE
        txtEmpty.visibility = View.VISIBLE
        bookRVAdapter.updateList(books)
        toast("No Book Yet").show()
    }

    override fun goToAddNewBook() {
        startActivity<AddBookActivity>()
    }

    override fun goToCurrency() {
        startActivity<CurrencyActivity>()
    }

    override fun goToSettings() {
        startActivity<SettingsActivity>()
    }

    /**
     * Item Click Listener Interface for Book RecyclerView
     */
    interface BookItemListener {
        fun onDetailClick(bookId: Int, bookTitle: String)

        fun onDeleteClick(bookId: Int, bookTitle: String)

        fun onEditClick(bookId: Int, bookTitle: String, bookDesc: String)

        fun onBookClick(bookId: Int, bookTitle: String)

        fun onAddExpenseClick(bookId: Int, bookTitle: String)
    }
}
