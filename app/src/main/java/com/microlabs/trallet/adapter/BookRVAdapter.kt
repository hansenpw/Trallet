package com.microlabs.trallet.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.microlabs.trallet.BR
import com.microlabs.trallet.MainActivity
import com.microlabs.trallet.R
import com.microlabs.trallet.databinding.ItemBooksBinding
import com.microlabs.trallet.model.Book

/**
 * Book RecyclerView Adapter
 */
class BookRVAdapter(private val itemListener: MainActivity.BookItemListener) :
        ListAdapter<Book, BookRVAdapter.ItemHolder>(Callback) {

    object Callback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            Log.d("rv", "rv items same")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            Log.d("rv", "rv contents same")
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = DataBindingUtil.inflate<ItemBooksBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_books,
                parent,
                false)
//        val view = LayoutInflater.from(parent.context).inflate(
//                R.layout.item_books,
//                parent,
//                false)
        return ItemHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemHolder(view: View) : ViewHolder(view) {
        private val binding: ItemBooksBinding?
            get() = DataBindingUtil.getBinding(itemView)

        fun bind(book: Book) {
//            view.lblTitle.text = book.title
//            view.lblDescription.text = book.desc
            binding?.setVariable(BR.book, book)
//            itemBinding.setVariable(BR.itemListener, itemListener)
            binding?.btnDelete?.setOnClickListener {
                itemListener.onDeleteClick(book)
            }
            binding?.btnDetails?.setOnClickListener {
                itemListener.onDetailClick(book.id, book.title)
            }
            binding?.btnEditCard?.setOnClickListener {
                itemListener.onEditClick(book.id, book.title, book.desc)
            }
            binding?.btnAddExpense?.setOnClickListener {
                itemListener.onAddExpenseClick(book.id, book.title)
            }
            binding?.executePendingBindings()
        }
    }
}
