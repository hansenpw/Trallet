package com.microlabs.trallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.microlabs.trallet.adapter.CurrencySpinnerAdapter;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.model.Expense;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class BookDetailActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    PieChart chart;
    @BindView(R.id.lblFoodTotal)
    TextView lblFoodTotal;
    @BindView(R.id.lblTransportTotal)
    TextView lblTransportTotal;
    @BindView(R.id.lblShopTotal)
    TextView lblShopTotal;
    @BindView(R.id.lblOthersTotal)
    TextView lblOthersTotal;
    @BindViews({R.id.btnSeeExpense, R.id.lblTotal, R.id.lblFoodTotal, R.id.lblTransport, R.id.lblTransportTotal,
            R.id.lblShopTotal, R.id.lblShop, R.id.lblOthers, R.id.lblOthersTotal, R.id.lblAll, R.id.lblAllTotal, R.id.spinnerCurrency})
    List<View> views;
    @BindView(R.id.spinnerCurrency)
    Spinner spinnerCurrency;
    @BindView(R.id.lblAllTotal)
    TextView lblAllTotal;
    private int bookId;
    private String bookTitle;
    private ButterKnife.Setter<? super View, Integer> changeVisibility = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(@NonNull View view, Integer value, int index) {
            view.setVisibility(value);
        }
    };
    private RealmResults<Currency> currencies;
    private double foodTotal;
    private double transportTotal;
    private double shopTotal;
    private double othersTotal;
    private double allTotal;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.apply(views, changeVisibility, View.GONE);

        bookId = getIntent().getIntExtra("bookId", 0);
        bookTitle = getIntent().getStringExtra("lblTitle");
        realm = Realm.getDefaultInstance();
        currencies = realm.where(Currency.class).findAll();

        setup();

        CurrencySpinnerAdapter adapterCurr = new CurrencySpinnerAdapter(this, R.layout.item_category, currencies);
        spinnerCurrency.setAdapter(adapterCurr);
        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency selectedItem = (Currency) spinnerCurrency.getSelectedItem();
                setCurrency(selectedItem.getName(), selectedItem.getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        RealmResults<Expense> realmResults = realm.where(Expense.class).equalTo(Expense.fBook, bookId).findAll();
        List<PieEntry> entries = new ArrayList<>();
        foodTotal = calculateTotalExpense(realmResults.where().equalTo(Expense.fCategory, 1).findAll());
//        lblFoodTotal.setText(foodTotal + "");
        PieEntry entry2 = new PieEntry((float) foodTotal);
        entry2.setLabel("Food");
        entries.add(entry2);
        transportTotal = calculateTotalExpense(realmResults.where().equalTo(Expense.fCategory, 2).findAll());
//        lblTransportTotal.setText(transportTotal + "");
        PieEntry entry1 = new PieEntry((float) transportTotal);
        entry1.setLabel("Transport");
        entries.add(entry1);
        shopTotal = calculateTotalExpense(realmResults.where().equalTo(Expense.fCategory, 3).findAll());
//        lblShopTotal.setText(shopTotal + "");
        PieEntry entry = new PieEntry((float) shopTotal);
        entry.setLabel("Shop");
        entries.add(entry);
        othersTotal = calculateTotalExpense(realmResults.where().equalTo(Expense.fCategory, 4).findAll());
//        lblOthersTotal.setText(othersTotal + "");
        PieEntry entry3 = new PieEntry((float) othersTotal);
        entry3.setLabel("Others");
        entries.add(entry3);
        allTotal = foodTotal + transportTotal + shopTotal + othersTotal;
//        lblAllTotal.setText(allTotal + "");

        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(new PieData(dataSet));
        chart.setUsePercentValues(true);
        chart.invalidate();
    }

    private void setCurrency(String name, double value) {
        lblFoodTotal.setText(name + " " + NumberFormat.getInstance().format(foodTotal / value));
        lblTransportTotal.setText(name + " " + NumberFormat.getInstance().format(transportTotal / value));
        lblShopTotal.setText(name + " " + NumberFormat.getInstance().format(shopTotal / value));
        lblOthersTotal.setText(name + " " + NumberFormat.getInstance().format(othersTotal / value));
        lblAllTotal.setText(name + " " + NumberFormat.getInstance().format(allTotal / value));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (chart.getVisibility() == View.VISIBLE) {
            menu.getItem(0).setEnabled(false);
        } else {
            menu.getItem(1).setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_view_chart) {
            chart.setVisibility(View.VISIBLE);
            ButterKnife.apply(views, changeVisibility, View.GONE);
            invalidateOptionsMenu();
        } else if (item.getItemId() == R.id.menu_view_list) {
            chart.setVisibility(View.GONE);
            ButterKnife.apply(views, changeVisibility, View.VISIBLE);
            invalidateOptionsMenu();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSeeExpense)
    public void onViewClicked() {
        Intent i = new Intent(BookDetailActivity.this, ExpenseActivity.class);
        i.putExtra("bookId", bookId);
        i.putExtra("lblTitle", bookTitle);
        startActivity(i);
    }

    private double calculateTotalExpense(List<Expense> expenses) {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getValue() * currencies.where().equalTo(Currency.fId, expense.getCurrencyId()).findFirst().getValue();
        }
        return total;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
