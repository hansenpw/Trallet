package com.microlabs.trallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.presenter.AddBookActivityPresenter;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddBookActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBookActivity extends AppCompatActivity implements AddBookActivityView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtEditTitle)
    TextInputEditText txtEditTitle;
    @BindView(R.id.txtEditDescription)
    TextInputEditText txtEditDescription;

    private AddBookActivityPresenter presenter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new AddBookActivityPresenter(this, new DatabaseBookRepository());

        Intent i = getIntent();
        id = i.getIntExtra("id", 0);
        if (id != 0) {
            txtEditTitle.setText(i.getStringExtra("lblTitle"));
            txtEditDescription.setText(i.getStringExtra("lblDescription"));
        }
    }

    @OnClick(R.id.fab)
    public void onClick() {
        String title = txtEditTitle.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Please Input Title", Toast.LENGTH_SHORT).show();
        } else {
            if (id == 0) {
                presenter.insertNewBook(title, txtEditDescription.getText().toString());
            } else {
                presenter.updateBook(id, title, txtEditDescription.getText().toString());
            }
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

    @Override
    public void showError() {
        Toast.makeText(this, "Fail to insert book", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void done() {
        finish();
    }

    @Override
    public void showBook(Book book) {
        id = book.getId();
        txtEditTitle.setText(book.getTitle());
        txtEditDescription.setText(book.getDesc());
    }

    @Override
    protected void onDestroy() {
        presenter.close();
        super.onDestroy();
    }
}
