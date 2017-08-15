package com.microlabs.trallet.presenter;

import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddCurrencyActivityView;

/**
 * Created by Hansen on 5/11/2017.
 * <p>
 * Add Currency Activity Presenter
 */

public class AddCurrencyActivityPresenter {

    private AddCurrencyActivityView view;
    private DatabaseBookRepository repo;

    public AddCurrencyActivityPresenter(AddCurrencyActivityView view, DatabaseBookRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    public void insertNewCurrency(String title, double value) {
        if (title.isEmpty() || value <= 0.0) {
            view.showError();
        } else {
            if (repo.saveCurrency(title, value))
                view.done();
            else
                view.showDuplicateError();
        }
    }

    public void updateCurrency(int id, String title, Double value) {
        if (id <= 0 || title.isEmpty() || value <= 0.0) {
            view.showError();
        } else {
            if (repo.updateCurrency(id, title, value)) {
                view.done();
            } else {
                view.showDuplicateError();
            }
        }
    }

    public void validateDeleteCurrency(int id) {
        if (repo.canCurrencyDelete(id)) {
            view.validateDeleteCurrency();
        } else {
            view.showFailDeleteCurrency();
        }
    }

    public void deleteCurrency(int id) {
        repo.deleteCurrency(id);
        view.done();
    }

    public void close() {
        repo.close();
    }
}
