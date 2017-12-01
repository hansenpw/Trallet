package com.microlabs.trallet.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.microlabs.trallet.AddCurrencyActivity
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Currency
import kotlinx.android.synthetic.main.item_currency.view.*
import org.jetbrains.anko.startActivity
import java.text.DecimalFormat
import java.util.*

/**
 * Currency RecyclerView Adapter
 */
class CurrencyRVAdapter(private val context: Context) : RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder>() {

    private val currencyList = ArrayList<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.item_currency, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateItem(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    fun updateList(currencyList: List<Currency>) {
        this.currencyList.clear()
        this.currencyList.addAll(currencyList)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        currencyList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun insert(item: Currency, position: Int) {
        currencyList.add(position, item)
        notifyItemInserted(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var item: Currency
        private var decimalFormat: DecimalFormat = DecimalFormat("###.#")
        val txtCurrName = itemView.txtCurrName
        val txtCurrPrice = itemView.txtCurrPrice
        val cvCurrency = itemView.cvCurrency

        internal fun updateItem(item: Currency) {
            this.item = item
            txtCurrName.text = item.name
            txtCurrPrice.text = decimalFormat.format(item.value)

            cvCurrency.setOnClickListener {
                context.startActivity<AddCurrencyActivity>("currId" to item.id,
                        "currName" to item.name, "currValue" to item.value)
            }
        }

    }
}
