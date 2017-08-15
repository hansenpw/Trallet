package com.microlabs.trallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.microlabs.trallet.adapter.BookRVAdapter;
import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.presenter.MainActivityPresenter;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.MainActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    @BindView(R.id.rvBook)
    RecyclerView rv;
    @BindView(R.id.txtEmpty)
    TextView txtEmpty;

    BookRVAdapter bookRVAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainActivityPresenter presenter;

    /**
     * Item Click Listener for Book RecyclerView
     */
    private BookItemListener itemListener = new BookItemListener() {
        @Override
        public void onDetailClick(int bookId, String bookTitle) {
            Intent i = new Intent(MainActivity.this, ExpenseActivity.class);
            i.putExtra("bookId", bookId);
            i.putExtra("lblTitle", bookTitle);
            startActivity(i);
        }

        @Override
        public void onDeleteClick(int bookId, String bookTitle) {
            validateDeleteBook(bookId, bookTitle);
        }

        @Override
        public void onEditClick(int bookId, String bookTitle, String bookDesc) {
            Intent i = new Intent(MainActivity.this, AddBookActivity.class);
            i.putExtra("id", bookId);
            i.putExtra("lblTitle", bookTitle);
            i.putExtra("lblDescription", bookDesc);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        DatabaseBookRepository bookRepository = new DatabaseBookRepository();
        presenter = new MainActivityPresenter(this, bookRepository);

        setup();
    }

    private void setup() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        bookRVAdapter = new BookRVAdapter(itemListener);
        rv.setAdapter(bookRVAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAdapterList();
    }

    /**
     * Update Data though Presenter
     */
    public void updateAdapterList() {
        // Update list of books sorted by its ID
        presenter.loadBooks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_currency) {
            presenter.toCurrency();
        } else if (item.getItemId() == R.id.menu_settings) {
            presenter.toSettings();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.checkDefault();
    }

    @Override
    protected void onDestroy() {
        // Close Realm Instance when Activity is Destroyed to prevent memory leaks
        presenter.close();
        super.onDestroy();
    }

    /**
     * Validation to Delete Book
     *
     * @param id    = bookId to remove
     * @param title = bookTitle to remove
     */
    public void validateDeleteBook(final int id, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure want to delete " + title + " book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                presenter.deleteBook(id);
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

    @Override
    public void showBooks(List<Book> books) {
        txtEmpty.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        bookRVAdapter.updateList(books);
    }

    @Override
    public void showNoBook(List<Book> books) {
        rv.setVisibility(View.GONE);
        txtEmpty.setVisibility(View.VISIBLE);
        bookRVAdapter.updateList(books);
        Toast.makeText(this, "There is no books.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToAddNewBook() {
        Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToCurrency() {
        Intent intent = new Intent(MainActivity.this, CurrencyActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        presenter.addNewBook();
    }

    /**
     * Item Click Listener Interface for Book RecyclerView
     */
    public interface BookItemListener {
        void onDetailClick(int bookId, String bookTitle);

        void onDeleteClick(int bookId, String bookTitle);

        void onEditClick(int bookId, String bookTitle, String bookDesc);
    }
}
