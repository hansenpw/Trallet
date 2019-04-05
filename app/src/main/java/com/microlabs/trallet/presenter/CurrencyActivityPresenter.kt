package com.microlabs.trallet.presenter

import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.CurrencyActivityView

/**
 * Created by Hansen on 5/11/2017.
 *
 * RCurrency Activity Presenter
 */

class CurrencyActivityPresenter(private val view: CurrencyActivityView?, private val repo: DatabaseBookRepository) {

    fun loadCurrency() {
//        view.showCurrencyList(repo.RCurrencyList)
    }

//    fun close() = repo.close()
}
