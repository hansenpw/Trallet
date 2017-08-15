package com.microlabs.trallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microlabs.trallet.AddExpensesActivity;
import com.microlabs.trallet.ExpenseActivity;
import com.microlabs.trallet.R;
import com.microlabs.trallet.base.RealmHelper;
import com.microlabs.trallet.model.Expense;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicholas on 3/13/2017.
 */

public class ExpenseRVAdapter extends RecyclerView.Adapter<ExpenseRVAdapter.ViewHolder> {

    Context context;
    List<Expense> expenseList = new ArrayList<>();

    public ExpenseRVAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<Expense> expenses) {
        expenseList.clear();
        expenseList.addAll(expenses);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_expense, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.updateItem(expense);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtExTitle)
        TextView txtExTitle;
        @BindView(R.id.txtExPrice)
        TextView txtExPrice;
        Expense item;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void updateItem(Expense item) {
            this.item = item;
            txtExTitle.setText(item.getTitle());
            txtExPrice.setText(String.valueOf(item.getValue()));
        }

        @OnClick({R.id.btnEdit, R.id.btnDelete, R.id.cvExpense})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnEdit:
                    Intent intent = new Intent(context, AddExpensesActivity.class);
                    intent.putExtra("bookId", item.getBookId());
                    intent.putExtra("fromAdapter", item.getId());
                    context.startActivity(intent);
                    break;
                case R.id.btnDelete:
                    ((ExpenseActivity) context).deleteExpense(item.getBookId(), item.getValue());
                    RealmHelper.deleteExpense(item.getId());
                    ((ExpenseActivity) context).setUpView();
                    break;
                case R.id.cvExpense:
                    break;
            }
        }
    }
}

























