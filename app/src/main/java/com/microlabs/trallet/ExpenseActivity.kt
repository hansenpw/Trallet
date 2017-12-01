package com.microlabs.trallet

import android.graphics.Canvas
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import com.microlabs.trallet.adapter.ExpenseRVAdapter
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.presenter.ExpenseActivityPresenter
import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.ExpenseActivityView
import kotlinx.android.synthetic.main.activity_expense.*
import kotlinx.android.synthetic.main.content_expense.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class ExpenseActivity : AppCompatActivity(), ExpenseActivityView {

    private lateinit var adapter: ExpenseRVAdapter
    private var bookId: Int = 0
    private val presenter: ExpenseActivityPresenter by lazy { ExpenseActivityPresenter(this, DatabaseBookRepository()) }

    private val expenseId = ArrayList<Int>()
    private var snackbar: Snackbar? = null

    /**
     * Item Click Listener for Expense RecyclerView
     */
    private val itemListener = object : ExpenseItemListener {
        override fun onEditClick(expenseId: Int) {
            presenter.editExpense(expenseId)
        }

        override fun onDeleteClick(expenseId: Int) {
            presenter.deleteExpense(bookId, expenseId)
            updateData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        bookId = intent.getIntExtra("bookId", -1)

        if (bookId != -1) {
            lblDescription_Expense.text = intent.getStringExtra("lblTitle")
        }

        setUpView()
    }

    fun setUpView() {
        adapter = ExpenseRVAdapter(itemListener)
        val mLayoutManager = LinearLayoutManager(applicationContext)

        rvExpenseList.layoutManager = mLayoutManager
        rvExpenseList.itemAnimator = DefaultItemAnimator()
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return ItemTouchHelper.Callback.makeMovementFlags(0, ItemTouchHelper.END or ItemTouchHelper.START)
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
                expenseId.add((viewHolder as ExpenseRVAdapter.ViewHolder).item.id)
                adapter.removeExpense(viewHolder.getAdapterPosition())
                snackbar!!.show()
            }

            override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                viewHolder.itemView.alpha = (viewHolder.itemView.width - Math.abs(dX)) / viewHolder.itemView.width
            }
        })
        itemTouchHelper.attachToRecyclerView(rvExpenseList)
        rvExpenseList.adapter = adapter

        fab.setOnClickListener {
            presenter.addNewExpense()
        }
    }

    private fun createSnackbar() {
        snackbar = Snackbar.make(currentFocus, "Expense Deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    expenseId.removeAt(0)
                    updateData()
                }.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_SWIPE || event == Snackbar.Callback.DISMISS_EVENT_MANUAL) {
                    presenter.deleteExpense(bookId, expenseId[0])
                    expenseId.removeAt(0)
                    if (expenseId.size == 0) {
                        updateData()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    /**
     * Updates Data through Presenter
     */
    private fun updateData() {
        presenter.loadExpense(bookId)
    }

    override fun onDestroy() {
        presenter.close()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun goToAddNewExpense(expenseId: Int) {
        startActivity<AddExpensesActivity>("bookId" to bookId, "fromAdapter" to expenseId)
    }

    override fun showNoExpense(expenses: List<Expense>) {
        adapter.updateData(expenses)
        toast("No Expense Yet")
    }

    override fun showExpenses(expenses: List<Expense>) {
        adapter.updateData(expenses)
    }

    /**
     * Item Click Listener Interface for Expense RecyclerView
     */
    interface ExpenseItemListener {
        fun onEditClick(expenseId: Int)

        fun onDeleteClick(expenseId: Int)
    }
}
