package com.microlabs.trallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.microlabs.trallet.model.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class AddNewBookActivity extends AppCompatActivity {

    @BindView(R.id.lblTitle_AddNewBook)
    EditText lblTitleAddNewBook;
    @BindView(R.id.lblDesciption_AddNewBook)
    EditText lblDesciptionAddNewBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btnAddNewBook)
    public void onClick() {
        if (lblTitleAddNewBook.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Input Title", Toast.LENGTH_SHORT).show();
        } else {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Book book = realm.createObject(Book.class);
            book.setId(realm);
            book.setTitle(lblTitleAddNewBook.getText().toString());
            book.setDesc(lblDesciptionAddNewBook.getText().toString());
            book.setTotal(0.0);
            realm.commitTransaction();
            realm.close();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
