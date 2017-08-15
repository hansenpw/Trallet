package com.microlabs.trallet.presenter;

import com.microlabs.trallet.model.Expense;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.ExpenseActivityView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hansen on 4/24/2017.
 *
 * Test for Expense Activity Presenter
 */
public class ExpenseActivityPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public DatabaseBookRepository repo;

    @Mock
    public ExpenseActivityView view;

    private ExpenseActivityPresenter presenter;

    private List<Expense> expenseList = Arrays.asList(new Expense(), new Expense(), new Expense());

    @Before
    public void createExpenseActivityPresenter() {
        presenter = new ExpenseActivityPresenter(view, repo);
    }

    /*@Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }*/

    @Test
    public void shouldReturnListOfExpenseWithBookIdToView() {
        when(repo.getExpenseList(0)).thenReturn(expenseList);
        presenter.loadExpense(0);
        verify(view).showExpenses(expenseList);
    }

    @Test
    public void shouldReturnNullToView() {
        when(repo.getExpenseList(0)).thenReturn(Collections.EMPTY_LIST);
        presenter.loadExpense(0);
        verify(view).showNoExpense(Collections.EMPTY_LIST);
    }

    @Test
    public void shouldGoToAddNewExpense() {
        presenter.addNewExpense();
        verify(view).goToAddNewExpense(0);
    }

    @Test
    public void shouldGoToEditExpense() {
        presenter.editExpense(1);
        verify(view).goToAddNewExpense(1);
    }

    @Test
    public void shouldDeleteExpense() {
        presenter.deleteExpense(1, 1);
        verify(repo, only()).deleteExpense(1, 1);
    }

    @Test
    public void shouldCloseBookRepo() {
        presenter.close();
        verify(repo, only()).close();
    }
}