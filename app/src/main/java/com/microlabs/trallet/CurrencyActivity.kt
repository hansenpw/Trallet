package com.microlabs.trallet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.microlabs.trallet.adapter.CurrencyRVAdapter
import com.microlabs.trallet.databinding.ActivityCurrencyBinding
import com.microlabs.trallet.viewmodel.CurrencyViewModel
import org.jetbrains.anko.startActivity

class CurrencyActivity : AppCompatActivity() {

    private lateinit var currencyRVAdapter: CurrencyRVAdapter
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var binding: ActivityCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpUI()

        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        viewModel.getAllCurrencies().observe(this, Observer {
            currencyRVAdapter.updateList(it)
        })
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
}
