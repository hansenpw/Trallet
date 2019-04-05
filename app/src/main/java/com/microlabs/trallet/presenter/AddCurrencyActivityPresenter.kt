package com.microlabs.trallet.presenter

import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.AddCurrencyActivityView

/**
 * Created by Hansen on 5/11/2017.
 *
 *
 * Add RCurrency Activity Presenter
 */

class AddCurrencyActivityPresenter(private val view: AddCurrencyActivityView, private val repo: DatabaseBookRepository) {

    fun insertNewCurrency(title: String, value: Double) {
        if (title.isEmpty() || value <= 0.0) {
            view.showError()
        } else {
//            if (repo.saveCurrency(title, value))
                view.done()
//            else
//                view.showDuplicateError()
        }
    }

    fun updateCurrency(id: Int, title: String, value: Double) {
        if (id <= 0 || title.isEmpty() || value <= 0.0) {
            view.showError()
        } else {
//            if (repo.updateCurrency(id, title, value)) {
                view.done()
//            } else {
//                view.showDuplicateError()
//            }
        }
    }

    fun validateDeleteCurrency(id: Int) {
//        if (repo.canCurrencyDelete(id)) {
            view.validateDeleteCurrency()
//        } else {
//            view.showFailDeleteCurrency()
//        }
    }

    fun deleteCurrency(id: Int) {
//        repo.deleteCurrency(id)
        view.done()
    }

//    fun close() = repo.close()
}
