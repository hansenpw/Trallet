package com.microlabs.trallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.repo.getCurrencyById
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by Nicholas on 3/13/2017.
 *
 * Currency Spinner Custom Adapter
 */

class CurrencySpinnerAdapter(context: Context, resource: Int, private val mCurrency: List<Currency>) : ArrayAdapter<Currency>(context, resource, mCurrency) {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = inflater.inflate(R.layout.item_category, parent, false)
        v.lblCategoryText.text = mCurrency[position].name
        return v
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = inflater.inflate(R.layout.item_category, parent, false)
        v.lblCategoryText.text = mCurrency[position].name
        return v
    }

    fun getPosition(currId: Int): Int {
        return super.getPosition(getCurrencyById(currId))
    }
}
