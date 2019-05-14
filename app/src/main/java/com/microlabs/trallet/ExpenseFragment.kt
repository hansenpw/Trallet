package com.microlabs.trallet


import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.microlabs.trallet.adapter.ExpenseRVAdapter
import com.microlabs.trallet.databinding.FragmentExpenseBinding
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.viewmodel.ExpenseViewModel


class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding
    private val viewModel: ExpenseViewModel by viewModels()
    private val args: ExpenseFragmentArgs by navArgs()

    private lateinit var adapter: ExpenseRVAdapter

    private val pendingDeleteExpenses = ArrayList<Expense>()
    private var expenses: List<Expense> = ArrayList()
    private var snackbar: Snackbar? = null

    /**
     * Item Click Listener for Expense RecyclerView
     */
    private val itemListener = object : ExpenseActivity.ExpenseItemListener {
        override fun onEditClick(expenseId: Int) {
//            startActivity<AddExpensesActivity>("bookId" to bookId, "fromAdapter" to expenseId)
            findNavController().navigate(ExpenseFragmentDirections.actionExpenseFragmentToAddExpenseFragment(args.book.id))
        }

        override fun onDeleteClick(expense: Expense) {
            viewModel.deleteExpense(expense)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()

        binding.lblDescriptionExpense.text = args.book.title

        viewModel.loadExpensesByBookId(args.book.id).observe(this, Observer {
            expenses = it
            adapter.updateData(it)
        })
    }

    private fun setUpView() {
        adapter = ExpenseRVAdapter(itemListener)
        val mLayoutManager = LinearLayoutManager(context)

        binding.rvExpenseList.layoutManager = mLayoutManager
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
        itemTouchHelper.attachToRecyclerView(binding.rvExpenseList)
        binding.rvExpenseList.adapter = adapter

        binding.fab.setOnClickListener {
            findNavController().navigate(ExpenseFragmentDirections.actionExpenseFragmentToAddExpenseFragment(args.book.id))
//            context!!.startActivity<AddExpensesActivity>("bookId" to args.book.id)
//            presenter.addNewExpense()
        }
    }

    private fun createSnackbar() {
        snackbar = Snackbar.make(activity!!.currentFocus!!, "Expense Deleted", Snackbar.LENGTH_SHORT)
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
//                                updateData()
                            }
                        }
                    }
                })
    }
}
