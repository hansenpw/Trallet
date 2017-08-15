package com.microlabs.trallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.microlabs.trallet.adapter.CurrencyRVAdapter;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.presenter.CurrencyActivityPresenter;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.CurrencyActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrencyActivity extends AppCompatActivity implements CurrencyActivityView {

    @BindView(R.id.rvCurrency)
    RecyclerView rvCurrency;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CurrencyRVAdapter currencyRVAdapter;

    private CurrencyActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new CurrencyActivityPresenter(this, new DatabaseBookRepository());
        setUpUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadCurrency();
    }

    private void setUpUI() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCurrency.setLayoutManager(manager);
        currencyRVAdapter = new CurrencyRVAdapter(this);
        rvCurrency.setAdapter(currencyRVAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCurrencyList(List<Currency> currencyList) {
        currencyRVAdapter.updateList(currencyList);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(CurrencyActivity.this, AddCurrencyActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.close();
    }
}
