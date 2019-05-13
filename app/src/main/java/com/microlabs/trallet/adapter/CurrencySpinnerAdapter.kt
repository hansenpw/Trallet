package com.microlabs.trallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Currency
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by Nicholas on 3/13/2017.
 *
 * RCurrency Spinner Custom Adapter
 */

class CurrencySpinnerAdapter(context: Context, resource: Int, private val currencyList: List<Currency>) : ArrayAdapter<Currency>(context, resource, currencyList) {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var itemView: View? = null

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = inflater.inflate(R.layout.item_category, parent, false)
        v.lblCategoryText.text = currencyList[position].name
        return v
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.item_category, parent, false)
        }
        itemView!!.lblCategoryText.text = currencyList[position].name
        return itemView!!
    }

    fun getPositionById(currencyId: Int): Int {
        return currencyList.indexOfFirst { it.id == currencyId }
    }
}
