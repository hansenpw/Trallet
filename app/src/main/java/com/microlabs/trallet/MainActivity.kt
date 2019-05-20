package com.microlabs.trallet

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.microlabs.trallet.base.BaseActivity
import com.microlabs.trallet.database.Book
import com.microlabs.trallet.databinding.ActivityMainBinding
import com.microlabs.trallet.viewmodel.MainViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layout: Int = R.layout.activity_main

//    private lateinit var bookRVAdapter: BookRVAdapter

    override fun initViewModel(): MainViewModel {
        return ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setSupportActionBar(binding.toolbar)

//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setup()

//        binding.fab.setOnClickListener {
//            startActivity<AddBookActivity>()
//        }
    }

    private fun setup() {
//        binding.content.rvBook.layoutManager = LinearLayoutManager(this)
//        binding.content.rvBook.itemAnimator = DefaultItemAnimator()
//        bookRVAdapter = BookRVAdapter(MainActivity.BookItemListener(this, validateDeleteBook))
//        binding.content.rvBook.adapter = bookRVAdapter

//        viewModel.loadBooks().observe(this, Observer {
//            if (it.isEmpty()) {
//                binding.content.rvBook.visibility = View.GONE
//                binding.content.txtEmpty.visibility = View.VISIBLE
//            } else {
//                binding.content.txtEmpty.visibility = View.GONE
//                binding.content.rvBook.visibility = View.VISIBLE
//            }
//            Timber.i("live: ${it.size}")
//            bookRVAdapter.submitList(it)
//        })

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.menu_currency) startActivity<CurrencyActivity>()
//        else if (item.itemId == R.id.menu_settings) startActivity<SettingsActivity>()
//
//        return super.onOptionsItemSelected(item)
//    }

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

        fun onEditClick(book: Book) {
            context.startActivity<AddBookActivity>("book" to book)
        }

        fun onBookClick(bookId: Int, bookTitle: String) {
            context.startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }

        fun onAddExpenseClick(bookId: Int, bookTitle: String) {
            context.startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }
    }
}
