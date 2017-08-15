package com.microlabs.trallet;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.microlabs.trallet.adapter.ExpenseRVAdapter;
import com.microlabs.trallet.model.Expense;
import com.microlabs.trallet.presenter.ExpenseActivityPresenter;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.ExpenseActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpenseActivity extends AppCompatActivity implements ExpenseActivityView {

    @BindView(R.id.lblDescription_Expense)
    TextView lblDescriptionExpense;
    @BindView(R.id.rvExpenseList)
    RecyclerView rvExpenseList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ExpenseRVAdapter adapter;
    private int bookId;
    private ExpenseActivityPresenter presenter;

    private ArrayList<Integer> expenseId = new ArrayList<>();
    private Snackbar snackbar;

    /**
     * Item Click Listener for Expense RecyclerView
     */
    private ExpenseItemListener itemListener = new ExpenseItemListener() {
        @Override
        public void onEditClick(int expenseId) {
            presenter.editExpense(expenseId);
        }

        @Override
        public void onDeleteClick(int expenseId) {
            presenter.deleteExpense(bookId, expenseId);
            updateData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ExpenseActivityPresenter(this, new DatabaseBookRepository());

        if ((bookId = getIntent().getIntExtra("bookId", -1)) != -1) {
            lblDescriptionExpense.setText(getIntent().getStringExtra("lblTitle"));
        }

        setUpView();
    }

    public void setUpView() {
        adapter = new ExpenseRVAdapter(itemListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        rvExpenseList.setLayoutManager(mLayoutManager);
        rvExpenseList.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.END | ItemTouchHelper.START);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (snackbar == null) {
                    createSnackbar();
                } else if (snackbar.isShown()) {
                    snackbar.dismiss();
                    createSnackbar();
                }
                expenseId.add(((ExpenseRVAdapter.ViewHolder) viewHolder).item.getId());
                adapter.removeExpense(viewHolder.getAdapterPosition());
                snackbar.show();
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                viewHolder.itemView.setAlpha((viewHolder.itemView.getWidth() - Math.abs(dX)) / viewHolder.itemView.getWidth());
            }
        });
        itemTouchHelper.attachToRecyclerView(rvExpenseList);
        rvExpenseList.setAdapter(adapter);
    }

    private void createSnackbar() {
        snackbar = Snackbar.make(getCurrentFocus(), "Expense Deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expenseId.remove(0);
                        updateData();
                    }
                }).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_SWIPE || event == DISMISS_EVENT_MANUAL) {
                            presenter.deleteExpense(bookId, expenseId.get(0));
                            expenseId.remove(0);
                            if (expenseId.size() == 0) {
                                updateData();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    /**
     * Updates Data through Presenter
     */
    private void updateData() {
        presenter.loadExpense(bookId);
    }

    @Override
    protected void onDestroy() {
        presenter.close();
        super.onDestroy();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        presenter.addNewExpense();
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
    public void showNoExpense(List<Expense> expenses) {
        adapter.updateData(expenses);
        Toast.makeText(this, "No Expense Yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExpenses(List<Expense> expenses) {
        adapter.updateData(expenses);
    }

    @Override
    public void goToAddNewExpense(int expenseId) {
        Intent i = new Intent(ExpenseActivity.this, AddExpensesActivity.class);
        i.putExtra("bookId", bookId);
        i.putExtra("fromAdapter", expenseId);
        startActivity(i);
    }

    /**
     * Item Click Listener Interface for Expense RecyclerView
     */
    public interface ExpenseItemListener {
        void onEditClick(int expenseId);

        void onDeleteClick(int expenseId);
    }
}
