package com.microlabs.trallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.microlabs.trallet.adapter.BookRVAdapter;
import com.microlabs.trallet.base.RealmHelper;
import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.model.Category;
import com.microlabs.trallet.model.Currency;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvBook)
    RecyclerView rv;

    Realm realm;
    BookRVAdapter bookRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewBookActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpUI();
    }

    public void updateAdapterList(){
        // Update list of books sorted by its ID
        bookRVAdapter.updateList(realm.where(Book.class).findAllSorted(Book.fId));
    }

    public void setUpUI() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        if (realm.where(Category.class).count() < 1) {
            setDefaultCategory();
        }
        if (realm.where(Currency.class).count() < 1) {
            setDefaultCurrency();
        }
        bookRVAdapter = new BookRVAdapter(this);
        bookRVAdapter.updateList(realm.where(Book.class).findAllSorted(Book.fId));
        rv.setAdapter(bookRVAdapter);
    }

    /**
     * Set up static default category
     */
    private void setDefaultCategory() {
        for (int i = 0; i < 4; i++) {
            realm.beginTransaction();
            Category c = realm.createObject(Category.class);
            c.setId(realm);
            switch (i) {
                case 0:
                    c.setName("Foods");
                    break;
                case 1:
                    c.setName("Transport");
                    break;
                case 2:
                    c.setName("Shop");
                    break;
                case 3:
                    c.setName("Others");
                    break;
            }
            realm.commitTransaction();
        }
    }

    /**
     * Set up static default currency
     */
    private void setDefaultCurrency() {
        for (int i = 0; i < 2; i++) {
            realm.beginTransaction();
            Currency c = realm.createObject(Currency.class);
            c.setId(realm);
            switch (i) {
                case 0:
                    c.setName("IDR Rp");
                    c.setValue(1.0);
                    break;
                case 1:
                    c.setName("USD $");
                    c.setValue(13500.0);
                    break;
            }
            realm.commitTransaction();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_currency) {
            Intent intent = new Intent(MainActivity.this, CurrencyActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // Close Realm Instance when Activity is Destroyed to prevent memory leaks
        realm.close();
        super.onDestroy();
    }

    public void validateDeleteBook(final int id, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure want to delete " + title + " book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                RealmHelper.deleteBook(realm, id);
                updateAdapterList();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
