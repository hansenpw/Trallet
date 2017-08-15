package com.microlabs.trallet.view;

/**
 * Created by Hansen on 5/11/2017.
 * <p>
 * Add Currency Activity View
 */

public interface AddCurrencyActivityView {
    void showError();

    void done();

    void showDuplicateError();

    void validateDeleteCurrency();

    void showFailDeleteCurrency();
}
