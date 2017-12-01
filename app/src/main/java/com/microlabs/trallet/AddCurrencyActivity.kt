package com.microlabs.trallet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.microlabs.trallet.presenter.AddCurrencyActivityPresenter
import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.AddCurrencyActivityView
import kotlinx.android.synthetic.main.activity_add_new_currency.*
import org.jetbrains.anko.*

class AddCurrencyActivity : AppCompatActivity(), AddCurrencyActivityView {

    private val presenter: AddCurrencyActivityPresenter by lazy { AddCurrencyActivityPresenter(this, DatabaseBookRepository()) }

    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_currency)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        id = intent.getIntExtra("currId", -1)
        if (id != -1) {
            txtCurrName.setText(intent.getStringExtra("currName"))
            txtCurrValue.setText(intent.getDoubleExtra("currValue", -1.0).toString())
            supportActionBar!!.title = "Edit Currency"
        }

        btnSave.setOnClickListener {
            // if new Currency is created
            if (txtCurrName.text.toString().isEmpty() || txtCurrValue.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Input all Data", Toast.LENGTH_SHORT).show()
            } else {
                if (id == -1) {
                    presenter.insertNewCurrency(txtCurrName.text.toString(), txtCurrValue.text.toString().toDouble())
                } else {
                    // if edit Currency
                    presenter.updateCurrency(id, txtCurrName.text.toString(), txtCurrValue.text.toString().toDouble())
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
            presenter.validateDeleteCurrency(id)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showError() {
        toast("Fail to save Currency").show()
    }

    override fun done() {
        finish()
    }

    override fun showDuplicateError() {
        alert {
            message = "Currency Name has exists. Please change the currency name to prevent duplication."
            okButton {
                it.dismiss()
            }
        }.show()
    }

    override fun validateDeleteCurrency() {
        alert {
            message = "Are you sure want to delete this currency?"
            yesButton { presenter.deleteCurrency(id) }
            noButton { it.dismiss() }
        }.show()
    }

    override fun showFailDeleteCurrency() {
        alert {
            message = "This currency is currently being used by some expense data. Please change the expense currency, to be able to remove this currency."
            okButton { it.dismiss() }
        }.show()
    }

    override fun onDestroy() {
        presenter.close()
        super.onDestroy()
    }
}
