package com.microlabs.trallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.microlabs.trallet.adapter.CurrencyRVAdapter;
import com.microlabs.trallet.model.Currency;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class CurrencyActivity extends AppCompatActivity {

    @BindView(R.id.rvCurrency)
    RecyclerView rvCurrency;

    CurrencyRVAdapter currencyRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCurrency.setLayoutManager(manager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrencyActivity.this, AddNewCurrencyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpUI();
    }

    private void setUpUI() {
        Realm realm = Realm.getDefaultInstance();
        currencyRVAdapter = new CurrencyRVAdapter(this);
        currencyRVAdapter.updateList(realm.where(Currency.class).findAll());
        rvCurrency.setAdapter(currencyRVAdapter);
        realm.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
