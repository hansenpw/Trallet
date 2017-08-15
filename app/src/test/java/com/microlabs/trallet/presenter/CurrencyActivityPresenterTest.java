package com.microlabs.trallet.presenter;

import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.CurrencyActivityView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hansen on 5/11/2017.
 *
 * Currency Activity Presenter Test
 */
public class CurrencyActivityPresenterTest {

    @Mock
    private CurrencyActivityView view;

    @Mock
    private DatabaseBookRepository repo;

    private CurrencyActivityPresenter presenter;

    private List<Currency> currencyList = Arrays.asList(new Currency(), new Currency(), new Currency());

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new CurrencyActivityPresenter(view, repo);
    }

    @Test
    public void shouldReturnAllCurrencyToView() throws Exception {
        when(repo.getCurrencyList()).thenReturn(currencyList);
        presenter.loadCurrency();
        verify(view).showCurrencyList(currencyList);
    }

}