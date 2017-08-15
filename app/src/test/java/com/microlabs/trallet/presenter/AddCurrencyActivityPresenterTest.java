package com.microlabs.trallet.presenter;

import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddCurrencyActivityView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hansen on 5/11/2017.
 *
 * Add Currency Activity Presenter Test
 */
public class AddCurrencyActivityPresenterTest {

    @Mock
    private AddCurrencyActivityView view;

    @Mock
    private DatabaseBookRepository repo;

    private AddCurrencyActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new AddCurrencyActivityPresenter(view, repo);
    }

    @Test
    public void shouldInsertNewCurrencyToRepo() {
        when(repo.saveCurrency("MONEY", 1.0)).thenReturn(true);
        presenter.insertNewCurrency("MONEY", 1.0);
        verify(view).done();
    }

    @Test
    public void shouldFailDuplicateInsertNewCurrencyToRepo() {
        when(repo.saveCurrency("MONEY", 1.0)).thenReturn(false);
        presenter.insertNewCurrency("MONEY", 1.0);
        verify(view).showDuplicateError();
    }

    @Test
    public void shouldFailToInsertNewCurrencyToRepo() {
        presenter.insertNewCurrency("", 0.0);
        verify(view).showError();
    }

    @Test
    public void shouldUpdateCurrencyToRepo() {
        presenter.updateCurrency(1, "MONEY", 1.0);
        verify(view).done();
    }

    @Test
    public void shouldFailToUpdateCurrencyToRepo() {
        presenter.updateCurrency(0, "", 0.0);
        verify(view).showError();
    }

    @Test
    public void shouldPassValidateDeleteCurrency() {
        when(repo.canCurrencyDelete(1)).thenReturn(true);
        presenter.validateDeleteCurrency(1);
        verify(view).validateDeleteCurrency();
    }

    @Test
    public void shouldFailValidateDeleteCurrency() {
        when(repo.canCurrencyDelete(1)).thenReturn(false);
        presenter.validateDeleteCurrency(1);
        verify(view).showFailDeleteCurrency();
    }

    @Test
    public void shouldDeleteCurrency() {
        presenter.deleteCurrency(1);
        verify(view).done();
    }
}