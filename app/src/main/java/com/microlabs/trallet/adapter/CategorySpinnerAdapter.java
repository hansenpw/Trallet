package com.microlabs.trallet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microlabs.trallet.R;
import com.microlabs.trallet.base.RealmHelper;
import com.microlabs.trallet.model.Category;

import java.util.ArrayList;

/**
 * Created by Nicholas on 3/13/2017.
 */

public class CategorySpinnerAdapter extends ArrayAdapter<Category>{

    private ArrayList<Category> mCategory;
    private LayoutInflater inflater;

    public CategorySpinnerAdapter(Context context, int resource, ArrayList<Category> categories) {
        super(context, resource, categories);
        mCategory = categories;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_category,parent,false);
        TextView lblCategory = (TextView) v.findViewById(R.id.lblCategoryText);
        ImageView imgCategory = (ImageView) v.findViewById(R.id.imgCategory);
        lblCategory.setText(mCategory.get(position).getName());
        imgCategory.setImageResource(setImage(position));
        return v;
    }

    private int setImage(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_local_dining_black_24dp;
            case 1:
                return R.drawable.ic_local_taxi_black_24dp;
            case 2:
                return R.drawable.ic_shopping_basket_black_24dp;
            default:
                return R.drawable.ic_local_atm_black_24dp;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_category,parent,false);
        TextView lblCategory = (TextView) v.findViewById(R.id.lblCategoryText);
        ImageView imgCategory = (ImageView) v.findViewById(R.id.imgCategory);
        lblCategory.setText(mCategory.get(position).getName());
        imgCategory.setImageResource(setImage(position));
        return v;
    }

    public int getPosition(int catId) {
        return super.getPosition(RealmHelper.getCategoryById(catId));
    }
}
