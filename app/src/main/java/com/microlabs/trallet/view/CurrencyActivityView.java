package com.microlabs.trallet.view;

import com.microlabs.trallet.model.Currency;

import java.util.List;

/**
 * Created by Hansen on 5/11/2017.
 *
 * Currency Activity View
 */

public interface CurrencyActivityView {

    void showCurrencyList(List<Currency> currencyList);
}
