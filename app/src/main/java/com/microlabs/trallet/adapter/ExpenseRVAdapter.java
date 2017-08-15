package com.microlabs.trallet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.microlabs.trallet.ExpenseActivity;
import com.microlabs.trallet.R;
import com.microlabs.trallet.model.Expense;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nicholas on 3/13/2017.
 * <p>
 * Expense RecyclerView Adapter
 */

public class ExpenseRVAdapter extends RecyclerView.Adapter<ExpenseRVAdapter.ViewHolder> {

    ExpenseActivity.ExpenseItemListener itemListener;
    private List<Expense> expenseList = new ArrayList<>();

    public ExpenseRVAdapter(ExpenseActivity.ExpenseItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void updateData(List<Expense> expenses) {
        expenseList.clear();
        expenseList.addAll(expenses);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_expense, null);
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

        @BindView(R.id.imgExpense)
        ImageView imgExpense;
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
            switch (item.getCategoryId()) {
                case 1:
                    imgExpense.setImageResource(R.drawable.ic_local_dining_black_24dp);
                    break;
                case 2:
                    imgExpense.setImageResource(R.drawable.ic_local_taxi_black_24dp);
                    break;
                case 3:
                    imgExpense.setImageResource(R.drawable.ic_shopping_basket_black_24dp);
                    break;
                default:
                    imgExpense.setImageResource(R.drawable.ic_local_atm_black_24dp);
                    break;
            }
        }

        @OnClick({R.id.btnEdit, R.id.btnDelete})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnEdit:
                    itemListener.onEditClick(item.getId());
                    break;
                case R.id.btnDelete:
                    itemListener.onDeleteClick(item.getId());
                    break;
            }
        }
    }
}
