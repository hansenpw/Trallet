package com.microlabs.trallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.microlabs.trallet.R;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.repo.RealmHelper;

import java.util.List;

/**
 * Created by Nicholas on 3/13/2017.
 *
 * Currency Spinner Custom Adapter
 */

public class CurrencySpinnerAdapter extends ArrayAdapter<Currency> {

    private List<Currency> mCurrency;
    private LayoutInflater inflater;

    public CurrencySpinnerAdapter(Context context, int resource, List<Currency> list) {
        super(context, resource, list);
        mCurrency = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_category,parent,false);
        TextView lblCategory = (TextView) v.findViewById(R.id.lblCategoryText);
        lblCategory.setText(mCurrency.get(position).getName());
        return v;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_category,parent,false);
        TextView lblCategory = (TextView) v.findViewById(R.id.lblCategoryText);
        lblCategory.setText(mCurrency.get(position).getName());
        return v;
    }

    public int getPosition(int currId) {
        return super.getPosition(RealmHelper.getCurrencyById(currId));
    }
}
