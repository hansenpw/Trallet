package com.microlabs.trallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.microlabs.trallet.adapter.ExpenseRVAdapter;
import com.microlabs.trallet.base.RealmHelper;
import com.microlabs.trallet.model.Expense;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.Sort;

public class ExpenseActivity extends AppCompatActivity {

    int bookId;
    String bookName;
    ExpenseRVAdapter adapter;
    @BindView(R.id.lblDescription_Expense)
    TextView lblDescriptionExpense;
    @BindView(R.id.rvExpenseList)
    RecyclerView rvExpenseList;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if ((bookId = getIntent().getIntExtra("bookId", -1)) != -1) {
            bookName = getIntent().getStringExtra("lblTitle");
            lblDescriptionExpense.setText(bookName);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpView();
    }

    public void setUpView() {
        adapter = new ExpenseRVAdapter(ExpenseActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }

        adapter.updateData(realm.where(Expense.class).equalTo(Expense.fBook, bookId).findAllSorted(Expense.fId, Sort.DESCENDING));
        rvExpenseList.setLayoutManager(mLayoutManager);
        rvExpenseList.setItemAnimator(new DefaultItemAnimator());
        rvExpenseList.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent i = new Intent(ExpenseActivity.this, AddExpensesActivity.class);
        i.putExtra("bookId", bookId);
        i.putExtra("fromAdapter", 0);
        startActivityForResult(i, RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteExpense(int bookId, double value) {
        RealmHelper.setBookDelete(realm, bookId, value);
    }

}


































