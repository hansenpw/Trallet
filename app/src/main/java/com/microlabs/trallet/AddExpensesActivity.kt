package com.microlabs.trallet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.microlabs.trallet.adapter.CategorySpinnerAdapter
import com.microlabs.trallet.adapter.CurrencySpinnerAdapter
import com.microlabs.trallet.databinding.ActivityAddExpensesBinding
import com.microlabs.trallet.model.Category
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.viewmodel.AddExpenseViewModel
import org.jetbrains.anko.toast

class AddExpensesActivity : AppCompatActivity() {

    private var bookId: Int = 0
    private var fromAdapterChecker: Int = -1 //Expense Id
    private var oldValue: Double = 0.0

    private lateinit var adapterCat: CategorySpinnerAdapter
    private lateinit var adapterCurr: CurrencySpinnerAdapter

    private lateinit var viewModel: AddExpenseViewModel
    private lateinit var binding: ActivityAddExpensesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expenses)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(AddExpenseViewModel::class.java)

        setUp()

        bookId = intent.getIntExtra("bookId", -1)
        if (bookId != -1) {
            fromAdapterChecker = intent.getIntExtra("fromAdapter", -1)
            if (fromAdapterChecker != -1) {
//                presenter.getExpenseData(fromAdapterChecker)
                viewModel.getExpenseById(fromAdapterChecker).observe(this, Observer {
                    setupData(it)
                })
            }
        }

        binding.fab.setOnClickListener {
            if (binding.txtExValue.text.toString().isEmpty() || binding.txtExTitle.text.toString().isEmpty()) {
                toast("Please Input All Required Data").show()
            } else {
                val value = binding.txtExValue.text.toString().toDouble()
                if (fromAdapterChecker == -1) {
                    viewModel.insertExpense(Expense(
                            title = binding.txtExTitle.text.toString(),
                            value = value,
                            categoryId = (binding.spinnerCategory.selectedItem as Category).id,
                            currencyId = (binding.spinnerCurrency.selectedItem as Currency).id,
                            bookId = bookId,
                            details = binding.txtDescription.text.toString()))
                    finish()
                } else {
                    viewModel.updateExpense(Expense(
                            id = fromAdapterChecker,
                            title = binding.txtExTitle.text.toString(),
                            value = value,
                            categoryId = (binding.spinnerCategory.selectedItem as Category).id,
                            currencyId = (binding.spinnerCurrency.selectedItem as Currency).id,
                            bookId = bookId,
                            details = binding.txtDescription.text.toString()
                    ))
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setUp() {
        viewModel.getAllCategory().observe(this, Observer {
            setupCategorySpinner(it)
        })
        viewModel.getAllCurrencies().observe(this, Observer {
            setupCurrencySpinner(it)
        })
    }

    /*@OnClick({R.id.fab, R.id.imgExpense})
    public void onClick(View view) {
        if (view.getId() == R.id.imgExpense) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE);
        } else {
            if (txtExValue.getText().toString().isEmpty() || txtExTitle.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Input All Required Data", Toast.LENGTH_SHORT).show();
            } else {
                double value = Double.parseDouble(txtExValue.getText().toString());
                if (fromAdapterChecker == 0) {
                    presenter.saveNewExpense(txtExTitle.getText().toString(), bookId, value,
                            ((RCategory) spinnerCategory.getSelectedItem()).getId(),
                            ((RCurrency) spinnerCurrency.getSelectedItem()).getId(),
                            Calendar.getInstance().getTime(), txtDescription.getText().toString());
                } else {
                    presenter.updateExpense(fromAdapterChecker, txtExTitle.getText().toString(), bookId, value,
                            ((RCategory) spinnerCategory.getSelectedItem()).getId(),
                            ((RCurrency) spinnerCurrency.getSelectedItem()).getId(),
                            Calendar.getInstance().getTime(), txtDescription.getText().toString(), oldValue);
                }
            }
        }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            imgExpense.setImageURI(imageData);

            *//*final String[] column = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imageData, column, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                String filepath = cursor.getString(cursor.getColumnIndex(column[0]));
                cursor.close();

                imgExpense.setImageBitmap(BitmapFactory.decodeFile(filepath));
            }*//*
        }
    }*/

    private fun setupData(expense: Expense) {
        binding.txtExTitle.setText(expense.title)
        binding.txtDescription.setText(expense.details)
        binding.txtExValue.setText(expense.value.toString())
        oldValue = expense.value
        binding.spinnerCurrency.setSelection(adapterCurr.getPositionById(expense.currencyId))
        binding.spinnerCategory.setSelection(adapterCat.getPositionById(expense.categoryId))
    }

    fun showError() {
        toast("Fail to Save RExpense").show()
    }

    fun done() {
        finish()
    }

    private fun setupCategorySpinner(categoryList: List<Category>) {
        adapterCat = CategorySpinnerAdapter(this, R.layout.item_category, categoryList)
        binding.spinnerCategory.adapter = adapterCat
    }

    private fun setupCurrencySpinner(currencyList: List<Currency>) {
        adapterCurr = CurrencySpinnerAdapter(this, R.layout.item_category, currencyList)
        binding.spinnerCurrency.adapter = adapterCurr
    }

    /*companion object {

        private val REQUEST_IMAGE = 1
    }*/
}
