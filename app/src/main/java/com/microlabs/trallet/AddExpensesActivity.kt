package com.microlabs.trallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.microlabs.trallet.adapter.CategorySpinnerAdapter
import com.microlabs.trallet.adapter.CurrencySpinnerAdapter
import com.microlabs.trallet.model.Category
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.presenter.AddExpenseActivityPresenter
import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.AddExpenseView
import kotlinx.android.synthetic.main.activity_add_expenses.*
import org.jetbrains.anko.toast
import java.util.*


class AddExpensesActivity : AppCompatActivity(), AddExpenseView {

    private var bookId: Int = 0
    private var fromAdapterChecker: Int = 0 //Expense Id
    private var oldValue: Double = 0.0

    private lateinit var adapterCat: CategorySpinnerAdapter
    private lateinit var adapterCurr: CurrencySpinnerAdapter
    private val presenter: AddExpenseActivityPresenter by lazy { AddExpenseActivityPresenter(this, DatabaseBookRepository()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expenses)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUp()

        bookId = intent.getIntExtra("bookId", -1)
        if (bookId != -1) {
            fromAdapterChecker = intent.getIntExtra("fromAdapter", 0)
            if (fromAdapterChecker != 0) {
                presenter.getExpenseData(fromAdapterChecker)
            }
        }

        fab.setOnClickListener {
            if (txtExValue.text.toString().isEmpty() || txtExTitle.text.toString().isEmpty()) {
                toast("Please Input All Required Data").show()
            } else {
                val value = txtExValue.text.toString().toDouble()
                if (fromAdapterChecker == 0) {
                    presenter.saveNewExpense(txtExTitle.text.toString(), bookId, value,
                            (spinnerCategory.selectedItem as Category).id,
                            (spinnerCurrency.selectedItem as Currency).id,
                            Calendar.getInstance().time, txtDescription.text.toString())
                } else {
                    presenter.updateExpense(fromAdapterChecker, txtExTitle.text.toString(), bookId, value,
                            (spinnerCategory.selectedItem as Category).id,
                            (spinnerCurrency.selectedItem as Currency).id,
                            Calendar.getInstance().time, txtDescription.text.toString(), oldValue)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setUp() {
        presenter.setupCategorySpinner()
        presenter.setupCurrencySpinner()
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
                            ((Category) spinnerCategory.getSelectedItem()).getId(),
                            ((Currency) spinnerCurrency.getSelectedItem()).getId(),
                            Calendar.getInstance().getTime(), txtDescription.getText().toString());
                } else {
                    presenter.updateExpense(fromAdapterChecker, txtExTitle.getText().toString(), bookId, value,
                            ((Category) spinnerCategory.getSelectedItem()).getId(),
                            ((Currency) spinnerCurrency.getSelectedItem()).getId(),
                            Calendar.getInstance().getTime(), txtDescription.getText().toString(), oldValue);
                }
            }
        }
    }*/

    /*@OnClick(R.id.fab)
    fun onClick(view: View) {
        *//*if (view.getId() == R.id.imgExpense) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE);
        } else {*//*
        if (txtExValue!!.text.toString().isEmpty() || txtExTitle!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Please Input All Required Data", Toast.LENGTH_SHORT).show()
        } else {
            val value = java.lang.Double.parseDouble(txtExValue!!.text.toString())
            if (fromAdapterChecker == 0) {
                presenter.saveNewExpense(txtExTitle!!.text.toString(), bookId, value,
                        (spinnerCategory!!.selectedItem as Category).id,
                        (spinnerCurrency!!.selectedItem as Currency).id,
                        Calendar.getInstance().time, txtDescription!!.text.toString())
            } else {
                presenter.updateExpense(fromAdapterChecker, txtExTitle!!.text.toString(), bookId, value,
                        (spinnerCategory!!.selectedItem as Category).id,
                        (spinnerCurrency!!.selectedItem as Currency).id,
                        Calendar.getInstance().time, txtDescription!!.text.toString(), oldValue)
            }
        }
        //        }
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

    override fun setupData(expense: Expense) {
        txtExTitle.setText(expense.title)
        txtDescription.setText(expense.details)
        txtExValue.setText(expense.value.toString())
        oldValue = expense.value
        spinnerCurrency.setSelection(adapterCurr.getPosition(expense.currencyId))
        spinnerCategory.setSelection(adapterCat.getPosition(expense.categoryId))
    }

    override fun showError() {
        toast("Fail to Save Expense").show()
    }

    override fun done() {
        finish()
    }

    override fun onDestroy() {
        presenter.close()
        super.onDestroy()
    }

    override fun setupCategorySpinner(categoryList: List<Category>) {
        adapterCat = CategorySpinnerAdapter(this, R.layout.item_category, categoryList)
        spinnerCategory.adapter = adapterCat
    }

    override fun setupCurrencySpinner(currencyList: List<Currency>) {
        adapterCurr = CurrencySpinnerAdapter(this, R.layout.item_category, currencyList)
        spinnerCurrency.adapter = adapterCurr
    }

    /*companion object {

        private val REQUEST_IMAGE = 1
    }*/
}
