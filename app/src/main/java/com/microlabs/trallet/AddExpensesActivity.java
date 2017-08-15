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
import com.microlabs.trallet.base.RealmHelper;
import com.microlabs.trallet.model.Category;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.model.Expense;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class AddExpensesActivity extends AppCompatActivity {

    int bookId;
    int fromAdapterChecker; //Expense Id

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
    Expense expense;

    ArrayList<Category> listCategory;
    ArrayList<Currency> listCurrency;
    Realm realm;
    CategorySpinnerAdapter adapterCat;
    CurrencySpinnerAdapter adapterCurr;

    @BindView(R.id.btnEditExpense)
    FloatingActionButton btnEditExpense;
    @BindView(R.id.btnAddExpense)
    FloatingActionButton btnAddExpense;
    private double oldValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        setUp();

        if ((bookId = getIntent().getIntExtra("bookId", -1)) != -1) {
            if ((fromAdapterChecker = getIntent().getIntExtra("fromAdapter", 0)) != 0) {
                expense = realm.where(Expense.class).equalTo(Expense.fId, fromAdapterChecker).findFirst();
                setUpData(expense);
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
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        listCategory = new ArrayList<>();
        listCurrency = new ArrayList<>();
        listCategory.addAll(realm.where(Category.class).findAll());
        listCurrency.addAll(realm.where(Currency.class).findAll());

        adapterCat = new CategorySpinnerAdapter(AddExpensesActivity.this, R.layout.item_category, listCategory);
        spinnerCategory.setAdapter(adapterCat);

        adapterCurr = new CurrencySpinnerAdapter(AddExpensesActivity.this, R.layout.item_category, listCurrency);
        spinnerCurrency.setAdapter(adapterCurr);
        btnAddExpense.setVisibility(View.VISIBLE);
        btnEditExpense.setVisibility(View.GONE);
    }

    private void setUpData(Expense expense) {
        txtExTitle.setText(expense.getTitle());
        txtDescription.setText(expense.getDetails());
        txtExValue.setText(String.valueOf(expense.getValue()));
        oldValue = expense.getValue();
        spinnerCurrency.setSelection(adapterCurr.getPosition(expense.getCurrencyId()));
        spinnerCategory.setSelection(adapterCat.getPosition(expense.getCategoryId()));
        btnAddExpense.setVisibility(View.GONE);
        btnEditExpense.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btnAddExpense, R.id.btnEditExpense})
    public void onClick(View view) {
        if (txtExValue.getText().toString().isEmpty() || txtExTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Input All Required Data", Toast.LENGTH_SHORT).show();
        } else {
            double value = Double.parseDouble(txtExValue.getText().toString());
            switch (view.getId()) {
                case R.id.btnAddExpense:
                    realm.beginTransaction();
                    Expense expense = realm.createObject(Expense.class);
                    expense.setId(realm);
                    expense.setTitle(txtExTitle.getText().toString());
                    expense.setValue(value);

                    Category cat = (Category) spinnerCategory.getSelectedItem();
                    expense.setCategoryId(cat.getId());
                    Currency curr = (Currency) spinnerCurrency.getSelectedItem();
                    expense.setCurrencyId(curr.getId());

                    expense.setDetails(txtDescription.getText().toString());
                    expense.setBookId(bookId);
                    expense.setDate(Calendar.getInstance().getTime());

                    realm.commitTransaction();

                    RealmHelper.setBookTotal(realm, bookId, value);

                    realm.close();

                    finish();
                    break;
                case R.id.btnEditExpense:
                    realm.beginTransaction();
                    Expense expenses = realm.where(Expense.class).equalTo(Expense.fId, fromAdapterChecker).findFirst();
                    expenses.setTitle(txtExTitle.getText().toString());
                    expenses.setValue(value);
                    expenses.setDetails(txtDescription.getText().toString());
                    Category cat1 = (Category) spinnerCategory.getSelectedItem();
                    expenses.setCategoryId(cat1.getId());
                    Currency curr1 = (Currency) spinnerCurrency.getSelectedItem();
                    expenses.setCurrencyId(curr1.getId());
                    expenses.setDate(Calendar.getInstance().getTime());
                    realm.commitTransaction();
                    RealmHelper.setBookEdit(realm, bookId, oldValue, value);
                    realm.close();
                    finish();
                    break;
            }
        }
    }
}
