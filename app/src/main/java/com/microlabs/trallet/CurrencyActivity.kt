package com.microlabs.trallet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.microlabs.trallet.adapter.CurrencyRVAdapter
import com.microlabs.trallet.databinding.ActivityCurrencyBinding
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.presenter.CurrencyActivityPresenter
import com.microlabs.trallet.repo.DatabaseBookRepository
import org.jetbrains.anko.startActivity

class CurrencyActivity : AppCompatActivity() {

    private lateinit var currencyRVAdapter: CurrencyRVAdapter

    private val presenter: CurrencyActivityPresenter by lazy {
        CurrencyActivityPresenter(null, DatabaseBookRepository())
    }

    private lateinit var binding: ActivityCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpUI()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCurrency()
    }

    private fun setUpUI() {
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.content.rvCurrency.layoutManager = manager
        currencyRVAdapter = CurrencyRVAdapter(this)
        binding.content.rvCurrency.adapter = currencyRVAdapter

        binding.fab.setOnClickListener {
            startActivity<AddCurrencyActivity>()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
//        presenter.close()
        super.onDestroy()
    }

    fun showCurrencyList(RCurrencyList: List<Currency>) {
        currencyRVAdapter.updateList(RCurrencyList)
    }
}
