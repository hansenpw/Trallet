package com.microlabs.trallet.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microlabs.trallet.ExpenseActivity
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Expense
import kotlinx.android.synthetic.main.item_expense.view.*
import java.util.*

/**
 * Created by Nicholas on 3/13/2017.
 *
 *
 * Expense RecyclerView Adapter
 */

class ExpenseRVAdapter(internal var itemListener: ExpenseActivity.ExpenseItemListener) : RecyclerView.Adapter<ExpenseRVAdapter.ViewHolder>() {
    private val expenseList = ArrayList<Expense>()

    fun updateData(expenses: List<Expense>) {
        expenseList.clear()
        expenseList.addAll(expenses)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.item_expense, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateItem(expenseList[position])
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    fun removeExpense(position: Int) {
        expenseList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var item: Expense
        val txtExTitle = itemView.txtExTitle
        val txtExPrice = itemView.txtExPrice
        val imgExpense = itemView.imgExpense
        val btnEdit = itemView.btnEdit
        val btnDelete = itemView.btnDelete
        val cvExpense = itemView.cvExpense

        internal fun updateItem(item: Expense) {
            this.item = item
            txtExTitle.text = item.title
            txtExPrice.text = item.value.toString()
            when (item.categoryId) {
                1 -> imgExpense.setImageResource(R.drawable.ic_local_dining_black_24dp)
                2 -> imgExpense.setImageResource(R.drawable.ic_local_taxi_black_24dp)
                3 -> imgExpense.setImageResource(R.drawable.ic_shopping_basket_black_24dp)
                else -> imgExpense.setImageResource(R.drawable.ic_local_atm_black_24dp)
            }

            btnEdit.setOnClickListener {
                itemListener.onEditClick(item.id)
            }
            btnDelete.setOnClickListener {
                itemListener.onDeleteClick(item)
            }
            cvExpense.setOnClickListener {
                itemListener.onEditClick(item.id)
            }
        }
    }
}
