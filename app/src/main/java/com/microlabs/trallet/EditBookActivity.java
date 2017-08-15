package com.microlabs.trallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.model.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class EditBookActivity extends AppCompatActivity {

    @BindView(R.id.txtEditTitle)
    TextInputEditText txtEditTitle;
    @BindView(R.id.txtEditDescription)
    TextInputEditText txtEditDescription;

    Realm realm;
    String title, description;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        id = i.getIntExtra("id",-1);
        title = i.getStringExtra("lblTitle");
        description = i.getStringExtra("lblDescription");

        txtEditTitle.setText(title);
        txtEditDescription.setText(description);

    }

    @OnClick(R.id.fabEditbook)
    public void onClick() {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Book book = realm.where(Book.class).equalTo(Book.fId, id).findFirst();
        book.setTitle(txtEditTitle.getText().toString());
        book.setDesc(txtEditDescription.getText().toString());
        realm.commitTransaction();
        realm.close();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
