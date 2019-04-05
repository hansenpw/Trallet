package com.microlabs.trallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by Nicholas on 3/13/2017.
 *
 * RCategory Spinner Custom Adapter
 */

class CategorySpinnerAdapter(context: Context, resource: Int, private val categoryList: List<Category>) : ArrayAdapter<Category>(context, resource, categoryList) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var itemView: View? = null

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = inflater.inflate(R.layout.item_category, parent, false)
        v.lblCategoryText.text = categoryList[position].name
        v.imgCategory.setImageResource(categoryList[position].imgId)
        return v
    }

    private fun setImage(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_local_dining_black_24dp
            1 -> R.drawable.ic_local_taxi_black_24dp
            2 -> R.drawable.ic_shopping_basket_black_24dp
            else -> R.drawable.ic_local_atm_black_24dp
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.item_category, parent, false)
        }
        itemView!!.lblCategoryText.text = categoryList[position].name
        itemView!!.imgCategory.setImageResource(categoryList[position].imgId)
        return itemView!!
    }

    fun getPositionById(categoryId: Int): Int {
        return categoryList.indexOfFirst { it.id == categoryId }
    }
}
