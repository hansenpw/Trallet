package com.microlabs.trallet

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.microlabs.trallet.adapter.BookRVAdapter
import com.microlabs.trallet.database.Book
import com.microlabs.trallet.databinding.FragmentBookListBinding
import com.microlabs.trallet.viewmodel.MainViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton
import timber.log.Timber

class BookListFragment : Fragment() {

    private lateinit var bookRVAdapter: BookRVAdapter
    private lateinit var binding: FragmentBookListBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setup()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBook.requestApplyInsets()

        binding.rvBook.setOnApplyWindowInsetsListener { v, insets ->
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, insets.systemWindowInsetBottom)
            val layoutParams = binding.fab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin += insets.systemWindowInsetBottom
            binding.rvBook.setOnApplyWindowInsetsListener(null)
            insets.consumeSystemWindowInsets()
        }
    }

    private fun setup() {
        binding.rvBook.layoutManager = LinearLayoutManager(context)
        binding.rvBook.itemAnimator = DefaultItemAnimator()
        bookRVAdapter = BookRVAdapter(BookItemListener(this, validateDeleteBook))
        binding.rvBook.adapter = bookRVAdapter



        viewModel.loadBooks().observe(this, Observer {
            if (it.isEmpty()) {
                binding.rvBook.visibility = View.GONE
                binding.txtEmpty.visibility = View.VISIBLE
            } else {
                binding.txtEmpty.visibility = View.GONE
                binding.rvBook.visibility = View.VISIBLE
            }
            Timber.i("live: ${it.size}")
            bookRVAdapter.submitList(it)
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(BookListFragmentDirections.actionBookListFragmentToAddBookFragment())
        }

        val toolbar = (activity as MainActivity).binding.toolbar
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {item ->
            if (item.itemId == R.id.menu_currency) context?.startActivity<CurrencyActivity>()
            else if (item.itemId == R.id.menu_settings) context?.startActivity<SettingsActivity>()
            true
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_currency) context?.startActivity<CurrencyActivity>()
        else if (item.itemId == R.id.menu_settings) context?.startActivity<SettingsActivity>()

        return super.onOptionsItemSelected(item)
    }

    /**
     * Validation to Delete Book
     */
    private val validateDeleteBook = fun(book: Book) {

        context?.alert("Are you sure want to delete ${book.title} book?", "Delete Confirmation") {
            yesButton {
                it.dismiss()
                viewModel.deleteBook(book)
            }
            noButton {
                it.dismiss()
            }
        }?.show()
    }

    /**
     * Item Click Listener Interface for Book RecyclerView
     */
    class BookItemListener(val context: BookListFragment, val validateDeleteBook: (Book) -> Unit) {
        fun onDetailClick(bookId: Int, bookTitle: String) {
//            context.startActivity<BookDetailActivity>("bookId" to bookId, "lblTitle" to bookTitle)
            context.context?.startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }

        fun onDeleteClick(book: Book) {
            validateDeleteBook(book)
        }

        fun onEditClick(book: Book) {
            context.findNavController().navigate(BookListFragmentDirections.actionBookListFragmentToAddBookFragment(book))
//            context.startActivity<AddBookActivity>("book" to book)
        }

        fun onBookClick(bookId: Int, bookTitle: String) {
//            context.findNavController().navigate(BookListFragmentDirections.actionBookListFragmentToExpenseFragment(bookId, bookTitle))
//            context.startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }

        fun onAddExpenseClick(book: Book) {
            context.findNavController().navigate(BookListFragmentDirections.actionBookListFragmentToExpenseFragment(book))
        }
    }

}
