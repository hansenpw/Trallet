package com.microlabs.trallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microlabs.trallet.AddCurrencyActivity;
import com.microlabs.trallet.R;
import com.microlabs.trallet.model.Currency;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Currency RecyclerView Adapter
 */
public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder> {

    private List<Currency> currencyList = new ArrayList<>();
    private Context context;

    public CurrencyRVAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_currency, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateItem(currencyList.get(position));
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public void updateList(List<Currency> currencyList) {
        this.currencyList.clear();
        this.currencyList.addAll(currencyList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtCurrName)
        TextView txtCurrName;
        @BindView(R.id.txtCurrPrice)
        TextView txtCurrPrice;
        DecimalFormat decimalFormat;
        Currency item;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            decimalFormat = new DecimalFormat("###.#");
        }

        void updateItem(Currency item) {
            this.item = item;
            txtCurrName.setText(item.getName());
            txtCurrPrice.setText(decimalFormat.format(item.getValue()));
        }

        @OnClick(R.id.cvCurrency)
        public void onClick() {
            Intent intent = new Intent(context, AddCurrencyActivity.class);
            intent.putExtra("currId", item.getId());
            intent.putExtra("currName", item.getName());
            intent.putExtra("currValue", item.getValue());
            context.startActivity(intent);
        }

    }
}



































