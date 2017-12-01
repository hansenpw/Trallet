package com.microlabs.trallet.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.microlabs.trallet.MainActivity
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Book
import kotlinx.android.synthetic.main.item_books.view.*
import java.util.*

/**
 * Book RecyclerView Adapter
 */
class BookRVAdapter(private val itemListener: MainActivity.BookItemListener) : RecyclerView.Adapter<BookRVAdapter.ViewHolder>() {

    private val bookList = ArrayList<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.item_books, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateItem(bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun updateList(bookList: List<Book>) {
        this.bookList.clear()
        this.bookList.addAll(bookList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var id: Int = 0
        val lblDescription = itemView.lblDescription
        val lblTitle = itemView.lblTitle
        val btnDetails = itemView.btnDetails
        val btnDelete = itemView.btnDelete
        val cvBooks = itemView.cvBooks
        val btnEditCard = itemView.btnEditCard
        val btnAddExpense = itemView.btnAddExpense

        fun updateItem(item: Book) {
            id = item.id
            lblDescription.text = item.desc
            lblTitle.text = item.title

            btnDetails.setOnClickListener {
                itemListener.onDetailClick(id, lblTitle.text.toString())
            }
            btnDelete.setOnClickListener {
                itemListener.onDeleteClick(id, lblTitle.text.toString())
            }
            cvBooks.setOnClickListener {
                itemListener.onBookClick(id, lblTitle.text.toString())
            }
            btnEditCard.setOnClickListener {
                itemListener.onEditClick(id, lblTitle.text.toString(), lblDescription.text.toString())
            }
            btnAddExpense.setOnClickListener {
                itemListener.onAddExpenseClick(id, lblTitle!!.text.toString())
            }
        }
    }
}
