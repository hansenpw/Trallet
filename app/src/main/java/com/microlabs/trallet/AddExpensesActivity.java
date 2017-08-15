package com.microlabs.trallet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.microlabs.trallet.adapter.CategorySpinnerAdapter;
import com.microlabs.trallet.adapter.CurrencySpinnerAdapter;
import com.microlabs.trallet.model.Category;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.model.Expense;
import com.microlabs.trallet.presenter.AddExpenseActivityPresenter;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddExpenseView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddExpensesActivity extends AppCompatActivity implements AddExpenseView {

    private static final int REQUEST_IMAGE = 1;

    @BindView(R.id.txtExTitle)
    EditText txtExTitle;
    @BindView(R.id.txtExValue)
    EditText txtExValue;
    @BindView(R.id.spinnerCurrency)
    Spinner spinnerCurrency;
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.txtDescription)
    EditText txtDescription;
    @BindView(R.id.fab)
    FloatingActionButton fab;
//    @BindView(R.id.imgExpense)
//    ImageView imgExpense;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int bookId;
    private int fromAdapterChecker; //Expense Id
    private double oldValue;

    private CategorySpinnerAdapter adapterCat;
    private CurrencySpinnerAdapter adapterCurr;
    private AddExpenseActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new AddExpenseActivityPresenter(this, new DatabaseBookRepository());

        setUp();

        if ((bookId = getIntent().getIntExtra("bookId", -1)) != -1) {
            if ((fromAdapterChecker = getIntent().getIntExtra("fromAdapter", 0)) != 0) {
                presenter.getExpenseData(fromAdapterChecker);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUp() {
        presenter.setupCategorySpinner();
        presenter.setupCurrencySpinner();
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

    @OnClick({R.id.fab})
    public void onClick(View view) {
        /*if (view.getId() == R.id.imgExpense) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE);
        } else {*/
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
//        }
    }

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

    @Override
    public void setupData(Expense expense) {
        txtExTitle.setText(expense.getTitle());
        txtDescription.setText(expense.getDetails());
        txtExValue.setText(String.valueOf(expense.getValue()));
        oldValue = expense.getValue();
        spinnerCurrency.setSelection(adapterCurr.getPosition(expense.getCurrencyId()));
        spinnerCategory.setSelection(adapterCat.getPosition(expense.getCategoryId()));
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Fail to Save Expense", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setupCategorySpinner(List<Category> categoryList) {
        adapterCat = new CategorySpinnerAdapter(AddExpensesActivity.this, R.layout.item_category, categoryList);
        spinnerCategory.setAdapter(adapterCat);
    }

    @Override
    public void setupCurrencySpinner(List<Currency> currencyList) {
        adapterCurr = new CurrencySpinnerAdapter(AddExpensesActivity.this, R.layout.item_category, currencyList);
        spinnerCurrency.setAdapter(adapterCurr);
    }

    @Override
    public void done() {
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.close();
        super.onDestroy();
    }
}
