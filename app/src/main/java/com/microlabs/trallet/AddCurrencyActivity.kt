package com.microlabs.trallet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.microlabs.trallet.databinding.ActivityAddNewCurrencyBinding
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.view.AddCurrencyActivityView
import com.microlabs.trallet.viewmodel.AddCurrencyViewModel
import org.jetbrains.anko.*

class AddCurrencyActivity : AppCompatActivity(), AddCurrencyActivityView {

    private var id: Int = 0

    private lateinit var binding: ActivityAddNewCurrencyBinding
    private lateinit var viewModel: AddCurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_currency)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this)[AddCurrencyViewModel::class.java]

        id = intent.getIntExtra("currId", -1)
        if (id != -1) {
            binding.txtCurrName.setText(intent.getStringExtra("currName"))
            binding.txtCurrValue.setText(intent.getDoubleExtra("currValue", -1.0).toString())
            supportActionBar!!.title = "Edit RCurrency"
        }

        binding.btnSave.setOnClickListener {
            // if new RCurrency is created
            if (binding.txtCurrName.text.toString().isEmpty() || binding.txtCurrValue.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Input all Data", Toast.LENGTH_SHORT).show()
            } else {
                if (id == -1) {
                    viewModel.insertCurrency(Currency(name = binding.txtCurrName.text.toString(), value = binding.txtCurrValue.text.toString().toDouble()))
//                    presenter.insertNewCurrency(binding.txtCurrName.text.toString(), binding.txtCurrValue.text.toString().toDouble())
                } else {
                    // if edit RCurrency
//                    presenter.updateCurrency(id, binding.txtCurrName.text.toString(), binding.txtCurrValue.text.toString().toDouble())
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (id != -1) {
            menuInflater.inflate(R.menu.menu_delete, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.menuDelete) {
//            presenter.validateDeleteCurrency(id)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showError() {
        toast("Fail to save RCurrency").show()
    }

    override fun done() {
        finish()
    }

    override fun showDuplicateError() {
        alert {
            message = "RCurrency Name has exists. Please change the currency name to prevent duplication."
            okButton {
                it.dismiss()
            }
        }.show()
    }

    override fun validateDeleteCurrency() {
        alert {
            message = "Are you sure want to delete this currency?"
            yesButton {
                //                presenter.deleteCurrency(id)
            }
            noButton { it.dismiss() }
        }.show()
    }

    override fun showFailDeleteCurrency() {
        alert {
            message = "This currency is currently being used by some expense data. Please change the expense currency, to be able to remove this currency."
            okButton { it.dismiss() }
        }.show()
    }
}
