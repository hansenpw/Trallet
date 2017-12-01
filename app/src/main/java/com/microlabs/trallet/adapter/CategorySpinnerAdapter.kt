package com.microlabs.trallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Category
import com.microlabs.trallet.repo.getCategoryById
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by Nicholas on 3/13/2017.
 *
 * Category Spinner Custom Adapter
 */

class CategorySpinnerAdapter(context: Context, resource: Int, private val mCategory: List<Category>) : ArrayAdapter<Category>(context, resource, mCategory) {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = inflater.inflate(R.layout.item_category, parent, false)
        v.lblCategoryText.text = mCategory[position].name
        v.imgCategory.setImageResource(setImage(position))
        return v
    }

    private fun setImage(position: Int): Int {
        when (position) {
            0 -> return R.drawable.ic_local_dining_black_24dp
            1 -> return R.drawable.ic_local_taxi_black_24dp
            2 -> return R.drawable.ic_shopping_basket_black_24dp
            else -> return R.drawable.ic_local_atm_black_24dp
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = inflater.inflate(R.layout.item_category, parent, false)
        v.lblCategoryText.text = mCategory[position].name
        v.imgCategory.setImageResource(setImage(position))
        return v
    }

    fun getPosition(catId: Int): Int {
        return super.getPosition(getCategoryById(catId))
    }
}
