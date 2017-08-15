package com.microlabs.trallet.presenter;

import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.CurrencyActivityView;

/**
 * Created by Hansen on 5/11/2017.
 *
 * Currency Activity Presenter
 */

public class CurrencyActivityPresenter {

    private CurrencyActivityView view;
    private DatabaseBookRepository repo;

    public CurrencyActivityPresenter(CurrencyActivityView view, DatabaseBookRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    public void loadCurrency() {
        view.showCurrencyList(repo.getCurrencyList());
    }

    public void close() {
        repo.close();
    }
}
