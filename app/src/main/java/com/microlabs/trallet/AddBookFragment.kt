package com.microlabs.trallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.microlabs.trallet.databinding.FragmentAddBookBinding
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.viewmodel.AddBookViewModel
import kotlinx.android.synthetic.main.content_add_new_book.*

class AddBookFragment : Fragment() {

    private val book: Book? = null

    private lateinit var binding: FragmentAddBookBinding
    private lateinit var viewModel: AddBookViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AddBookViewModel::class.java)

        if (book != null) {
            binding.txtEditTitle.setText(book.title)
            binding.txtEditDescription.setText(book.desc)
        }

        binding.fab.setOnClickListener {
            val title = binding.txtEditTitle.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(context, "Please Input Title", Toast.LENGTH_SHORT).show()
            } else {
                if (book == null) {
                    viewModel.insertNewBook(title, txtEditDescription.text.toString())
                } else {
                    viewModel.updateBook(book.id, title, txtEditDescription.text.toString())
                }
            }
        }
    }


}
