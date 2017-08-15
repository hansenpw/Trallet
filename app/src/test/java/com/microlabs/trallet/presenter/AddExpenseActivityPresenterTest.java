package com.microlabs.trallet.presenter;

import com.microlabs.trallet.model.Category;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.model.Expense;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddExpenseView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hansen on 4/28/2017.
 *
 * Test for Add Expense Activity Presenter
 */
public class AddExpenseActivityPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    public AddExpenseView view;

    @Mock
    public DatabaseBookRepository repo;

    private AddExpenseActivityPresenter presenter;

    private Expense expense = new Expense();

    @Before
    public void setUp() throws Exception {
        presenter = new AddExpenseActivityPresenter(view, repo);
    }

    /*@Test
    public void shouldPass() throws Exception {
        Assert.assertEquals(1, 1);
    }*/

    @Test
    public void shouldFillInValues() throws Exception {
        when(repo.getExpense(1)).thenReturn(expense);
        presenter.getExpenseData(1);
        verify(view).setupData(expense);
    }

    @Test
    public void shouldFailToInsertNewExpense() throws Exception {
        presenter.saveNewExpense("", 0, -1.0, 0, 0, null, "");
        verify(view).showError();
    }

    @Test
    public void shouldInsertNewExpense() throws Exception {
        presenter.saveNewExpense("asdf", 1, 0.1, 1, 1, Calendar.getInstance().getTime(), "asdf");
        verify(view).done();
    }

    @Test
    public void shouldSetupCategory() throws Exception {
        final List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());
        when(repo.getCategoryList()).thenReturn(categoryList);
        presenter.setupCategorySpinner();
        verify(view).setupCategorySpinner(categoryList);
    }

    @Test
    public void shouldSetupCurrency() throws Exception {
        final List<Currency> currencyList = Arrays.asList(new Currency(), new Currency(), new Currency());
        when(repo.getCurrencyList()).thenReturn(currencyList);
        presenter.setupCurrencySpinner();
        verify(view).setupCurrencySpinner(currencyList);
    }

    @Test
    public void shouldUpdateExpense() throws Exception {
        presenter.updateExpense(1, "asdf", 1, 0.1, 1, 1, Calendar.getInstance().getTime(), "asdf", 0.3);
        verify(view).done();
    }

    @Test
    public void shouldFailUpdateExpense() throws Exception {
        presenter.updateExpense(0, "asdf", 1, 0.1, 1, 1, Calendar.getInstance().getTime(), "asdf", 0.3);
        verify(view).showError();
    }
}