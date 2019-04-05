package com.microlabs.trallet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.content_book_detail.*
import org.jetbrains.anko.startActivity
import java.text.NumberFormat

class BookDetailActivity : AppCompatActivity() {

    private val views: List<View> by lazy { listOf(btnSeeExpense, lblTotal, lblFoodTotal, lblTransport, lblTransportTotal, lblShopTotal, lblShop, lblOthers, lblOthersTotal, lblAll, lblAllTotal, spinnerCurrency) }
    private var bookId: Int = 0
    private var bookTitle: String = ""
//    private var RCurrencies: RealmResults<RCurrency>? = null
    private var foodTotal: Double = 0.0
    private var transportTotal: Double = 0.0
    private var shopTotal: Double = 0.0
    private var othersTotal: Double = 0.0
    private var allTotal: Double = 0.0
//    private val realm: Realm by lazy { Realm.getDefaultInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        views.forEach { it.visibility = GONE }

        bookId = intent.getIntExtra("bookId", 0)
        bookTitle = intent.getStringExtra("lblTitle")
//        RCurrencies = realm.where(RCurrency::class.java).findAll()

        setup()

//        val adapterCurr = CurrencySpinnerAdapter(this, R.layout.item_category, item_categoryRCurrencies!!)
//        spinnerCurrency.adapter = adapterCurr
        spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = spinnerCurrency.selectedItem as Currency
                setCurrency(selectedItem.name, selectedItem.value)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btnSeeExpense.setOnClickListener {
            startActivity<ExpenseActivity>("bookId" to bookId, "lblTitle" to bookTitle)
        }
    }

    override fun onStart() {
        super.onStart()
        setup()
    }

    private fun setup() {
//        val realmResults = realm.where(RExpense::class.java).equalTo(RExpense.fBook, bookId).findAll()
//        val entries = ArrayList<PieEntry>()
//        foodTotal = calculateTotalExpense(realmResults.where().equalTo(RExpense.fCategory, 1.toInt()).findAll())
//        //        lblFoodTotal.setText(foodTotal + "");
//        val entry2 = PieEntry(foodTotal.toFloat())
//        entry2.label = "Food"
//        entries.add(entry2)
//        transportTotal = calculateTotalExpense(realmResults.where().equalTo(RExpense.fCategory, 2.toInt()).findAll())
//        //        lblTransportTotal.setText(transportTotal + "");
//        val entry1 = PieEntry(transportTotal.toFloat())
//        entry1.label = "Transport"
//        entries.add(entry1)
//        shopTotal = calculateTotalExpense(realmResults.where().equalTo(RExpense.fCategory, 3.toInt()).findAll())
//        //        lblShopTotal.setText(shopTotal + "");
//        val entry = PieEntry(shopTotal.toFloat())
//        entry.label = "Shop"
//        entries.add(entry)
//        othersTotal = calculateTotalExpense(realmResults.where().equalTo(RExpense.fCategory, 4.toInt()).findAll())
//        //        lblOthersTotal.setText(othersTotal + "");
//        val entry3 = PieEntry(othersTotal.toFloat())
//        entry3.label = "Others"
//        entries.add(entry3)
//        allTotal = foodTotal + transportTotal + shopTotal + othersTotal
//        //        lblAllTotal.setText(allTotal + "");
//
//        val dataSet = PieDataSet(entries, "Categories")
//        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
//
//        chart!!.data = PieData(dataSet)
//        chart!!.setUsePercentValues(true)
//        chart!!.invalidate()
    }

    private fun setCurrency(name: String, value: Double) {
        lblFoodTotal.text = name + " " + NumberFormat.getInstance().format(foodTotal / value)
        lblTransportTotal.text = name + " " + NumberFormat.getInstance().format(transportTotal / value)
        lblShopTotal.text = name + " " + NumberFormat.getInstance().format(shopTotal / value)
        lblOthersTotal.text = name + " " + NumberFormat.getInstance().format(othersTotal / value)
        lblAllTotal.text = name + " " + NumberFormat.getInstance().format(allTotal / value)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (chart!!.visibility == View.VISIBLE) {
            menu.getItem(0).isEnabled = false
        } else {
            menu.getItem(1).isEnabled = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_view_chart) {
            chart!!.visibility = View.VISIBLE
            views.forEach { it.visibility = GONE }
            invalidateOptionsMenu()
        } else if (item.itemId == R.id.menu_view_list) {
            chart!!.visibility = View.GONE
            views.forEach { it.visibility = VISIBLE }
            invalidateOptionsMenu()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun calculateTotalExpense(expens: List<Expense>): Double {
        var total = 0.0
        for (expense in expens) {
//            total += expense.value * RCurrencies!!.where().equalTo(RCurrency.fId, expense.currencyId).findFirst()!!.value
        }
        return total
    }
}
