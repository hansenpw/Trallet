package com.microlabs.trallet

import android.graphics.Canvas
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.microlabs.trallet.adapter.ExpenseRVAdapter
import com.microlabs.trallet.databinding.ActivityExpenseBinding
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.view.ExpenseActivityView
import com.microlabs.trallet.viewmodel.ExpenseViewModel
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

open class ExpenseActivity : AppCompatActivity(), ExpenseActivityView {

    private lateinit var adapter: ExpenseRVAdapter
    private var bookId: Int = 0
//    private val presenter: ExpenseActivityPresenter by lazy { ExpenseActivityPresenter(this, DatabaseBookRepository()) }

    private lateinit var viewModel: ExpenseViewModel
    private lateinit var binding: ActivityExpenseBinding

    private val pendingDeleteExpenses = ArrayList<Expense>()
    private var expenses: List<Expense> = ArrayList()
    private var snackbar: Snackbar? = null

    /**
     * Item Click Listener for Expense RecyclerView
     */
    private val itemListener = object : ExpenseItemListener {
        override fun onEditClick(expenseId: Int) {
            goToAddNewExpense(expenseId)
        }

        override fun onDeleteClick(expense: Expense) {
            viewModel.deleteExpense(expense)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        bookId = intent.getIntExtra("bookId", -1)

        if (bookId != -1) {
            binding.content.lblDescriptionExpense.text = intent.getStringExtra("lblTitle")
        }

        viewModel = ViewModelProviders.of(this).get(ExpenseViewModel::class.java)

        setUpView()

        viewModel.loadExpensesByBookId(bookId).observe(this, Observer {
            expenses = it
            adapter.updateData(it)
        })
    }

    private fun setUpView() {
        adapter = ExpenseRVAdapter(itemListener)
        val mLayoutManager = LinearLayoutManager(applicationContext)

        binding.content.rvExpenseList.setOnApplyWindowInsetsListener { v, insets ->
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, insets.systemWindowInsetBottom)
            insets
        }

        binding.content.rvExpenseList.layoutManager = mLayoutManager
//        rvExpenseList.itemAnimator = DefaultItemAnimator()
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return makeMovementFlags(0, ItemTouchHelper.END or ItemTouchHelper.START)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (snackbar == null) {
                    createSnackbar()
                } else if (snackbar!!.isShown) {
                    snackbar!!.dismiss()
                    createSnackbar()
                }
                pendingDeleteExpenses.add((viewHolder as ExpenseRVAdapter.ViewHolder).item)
                adapter.removeExpense(viewHolder.getAdapterPosition())
                snackbar!!.show()
            }

            override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                viewHolder.itemView.alpha = (viewHolder.itemView.width - Math.abs(dX)) / viewHolder.itemView.width
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.content.rvExpenseList)
        binding.content.rvExpenseList.adapter = adapter

        binding.fab.setOnClickListener {
            startActivity<AddExpensesActivity>("bookId" to bookId)
//            presenter.addNewExpense()
        }
    }

    private fun createSnackbar() {
        snackbar = Snackbar.make(currentFocus!!, "Expense Deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    pendingDeleteExpenses.removeAt(0)
                    adapter.updateData(expenses)
                }.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_SWIPE || event == DISMISS_EVENT_MANUAL) {
                            viewModel.deleteExpense(pendingDeleteExpenses[0])
                            pendingDeleteExpenses.removeAt(0)
                            if (pendingDeleteExpenses.size == 0) {
                                updateData()
                            }
                        }
                    }
                })
    }

    /**
     * Updates Data through Presenter
     */
    private fun updateData() {
//        presenter.loadExpense(bookId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun goToAddNewExpense(expenseId: Int) {
        startActivity<AddExpensesActivity>("bookId" to bookId, "fromAdapter" to expenseId)
    }

    override fun showNoExpense(expens: List<Expense>) {
//        adapter.updateData(expens)
        toast("No Expense Yet")
    }

    override fun showExpenses(expens: List<Expense>) {
//        adapter.updateData(expens)
    }

    /**
     * Item Click Listener Interface for RExpense RecyclerView
     */
    interface ExpenseItemListener {
        fun onEditClick(expenseId: Int)

        fun onDeleteClick(expense: Expense)
    }
}
