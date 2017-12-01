package com.microlabs.trallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.microlabs.trallet.adapter.CurrencyRVAdapter
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.presenter.CurrencyActivityPresenter
import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.CurrencyActivityView
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.content_currency.*
import org.jetbrains.anko.startActivity

class CurrencyActivity : AppCompatActivity(), CurrencyActivityView {

    private lateinit var currencyRVAdapter: CurrencyRVAdapter

    private val presenter: CurrencyActivityPresenter by lazy {
        CurrencyActivityPresenter(this, DatabaseBookRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpUI()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCurrency()
    }

    private fun setUpUI() {
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCurrency.layoutManager = manager
        currencyRVAdapter = CurrencyRVAdapter(this)
        rvCurrency.adapter = currencyRVAdapter

        fab.setOnClickListener {
            startActivity<AddCurrencyActivity>()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.close()
        super.onDestroy()
    }

    override fun showCurrencyList(currencyList: List<Currency>) {
        currencyRVAdapter.updateList(currencyList)
    }
}
