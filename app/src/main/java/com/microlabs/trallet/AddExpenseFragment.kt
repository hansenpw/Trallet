package com.microlabs.trallet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.microlabs.trallet.adapter.CategorySpinnerAdapter
import com.microlabs.trallet.adapter.CurrencySpinnerAdapter
import com.microlabs.trallet.base.hideKeyboard
import com.microlabs.trallet.databinding.FragmentAddExpenseBinding
import com.microlabs.trallet.model.Category
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.viewmodel.AddExpenseViewModel
import org.jetbrains.anko.toast
import timber.log.Timber


class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding
    private val viewModel: AddExpenseViewModel by viewModels()
    private val args: AddExpenseFragmentArgs by navArgs()

    private var adapterCat: CategorySpinnerAdapter? = null
    private var adapterCurr: CurrencySpinnerAdapter? = null

    private var oldValue: Double = 0.0
    private var oldExpense: Expense? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

        binding.fab.setOnClickListener {
            if (binding.txtExValue.text.toString().isEmpty() || binding.txtExTitle.text.toString().isEmpty()) {
                context!!.toast("Please Input All Required Data").show()
            } else {
                val value = binding.txtExValue.text.toString().toDouble()
                if (args.expenseId == -1) {
                    viewModel.insertExpense(Expense(
                            title = binding.txtExTitle.text.toString(),
                            value = value,
                            categoryId = (binding.spinnerCategory.selectedItem as Category).id,
                            currencyId = (binding.spinnerCurrency.selectedItem as Currency).id,
                            bookId = args.bookId,
                            details = binding.txtDescription.text.toString()))
                    hideKeyboard()
                    findNavController().popBackStack()
                } else {
                    viewModel.updateExpense(Expense(
                            id = args.expenseId,
                            title = binding.txtExTitle.text.toString(),
                            value = value,
                            categoryId = (binding.spinnerCategory.selectedItem as Category).id,
                            currencyId = (binding.spinnerCurrency.selectedItem as Currency).id,
                            bookId = args.bookId,
                            details = binding.txtDescription.text.toString()
                    ))
                    hideKeyboard()
                    findNavController().popBackStack()
                }
            }
        }

//        if (args.bookId != -1) {
            if (args.expenseId != -1) {
                viewModel.getExpenseById(args.expenseId).observe(this, Observer {
                    if (!isRemoving) {
                        setupData(it)
                    }
                })
            }
//        }
    }

    private fun setupData(expense: Expense) {
        oldExpense = expense
        binding.txtExTitle.setText(expense.title)
        binding.txtDescription.setText(expense.details)
        binding.txtExValue.setText(expense.value.toString())
        oldValue = expense.value
        if (adapterCat != null) {
            binding.spinnerCategory.setSelection(adapterCat!!.getPositionById(expense.categoryId), true)
        }
        if (adapterCurr != null) {
            binding.spinnerCurrency.setSelection(adapterCurr!!.getPositionById(expense.currencyId), true)
        }
        viewModel.getExpenseById(args.expenseId).removeObservers(this)
    }

    private fun setUp() {
        viewModel.getAllCategory().observe(this, Observer {
            setupCategorySpinner(it)
        })
        viewModel.getAllCurrencies().observe(this, Observer {
            setupCurrencySpinner(it)
        })
    }

    private fun setupCategorySpinner(categoryList: List<Category>) {
        adapterCat = CategorySpinnerAdapter(activity!!, R.layout.item_category, categoryList)
        binding.spinnerCategory.adapter = adapterCat
        binding.spinnerCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                context!!.toast("not")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Timber.d("spinner-selected $position")
            }
        }
        if (oldExpense != null) {
            binding.spinnerCategory.setSelection(adapterCat!!.getPositionById(oldExpense!!.categoryId), true)
        }
    }

    private fun setupCurrencySpinner(currencyList: List<Currency>) {
        adapterCurr = CurrencySpinnerAdapter(activity!!, R.layout.item_category, currencyList)
        binding.spinnerCurrency.adapter = adapterCurr
        if (oldExpense != null) {
            binding.spinnerCurrency.setSelection(adapterCurr!!.getPositionById(oldExpense!!.currencyId), true)
        }
    }
}
