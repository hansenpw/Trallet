package com.microlabs.trallet.presenter;

import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.MainActivityView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hansen on 4/24/2017.
 *
 * Test for Main Activity Presenter
 */
public class MainActivityPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public DatabaseBookRepository repo;

    @Mock
    public MainActivityView view;

    private MainActivityPresenter presenter;

    private List<Book> bookList = Arrays.asList(new Book(), new Book(), new Book());

    @Before
    public void createMainActivityPresenter() {
        presenter = new MainActivityPresenter(view, repo);
    }

    /*@Test
    public void shouldPassTest() throws Exception {
        assertEquals(1, 1);
    }*/

    /*@Test
    public void shouldReturnListOfBook() throws Exception {
        when(repo.getBookList()).thenReturn(null);
        List<Book> books = repo.getBookList();
        assertEquals(books, null);
        assertNull(books);
    }*/

    @Test
    public void shouldReturnBookListToView() throws Exception {
        when(repo.getBookList()).thenReturn(bookList);
        presenter.loadBooks();
        verify(view).showBooks(bookList);
    }

    @Test
    public void shouldDisplayNoBook() throws Exception {
        when(repo.getBookList()).thenReturn(Collections.EMPTY_LIST);
        presenter.loadBooks();
        verify(view).showNoBook(Collections.EMPTY_LIST);
    }

    @Test
    public void shouldDefaultCategoryNotExist() throws Exception {
        when(repo.isDefaultCategoryNotExist()).thenReturn(true);
        presenter.checkDefault();
        verify(repo).loadDefaultCategory();
    }

    @Test
    public void shouldDefaultCurrencyNotExist() throws Exception {
        when(repo.isDefaultCurrencyNotExist()).thenReturn(true);
        presenter.checkDefault();
        verify(repo).loadDefaultCurrency();
    }

    @Test
    public void shouldDefaultCategoryExist() throws Exception {
        when(repo.isDefaultCategoryNotExist()).thenReturn(false);
        presenter.checkDefault();
        verify(repo, never()).loadDefaultCategory();
    }

    @Test
    public void shouldDefaultCurrencyExist() throws Exception {
        when(repo.isDefaultCurrencyNotExist()).thenReturn(false);
        presenter.checkDefault();
        verify(repo, never()).loadDefaultCurrency();
    }

    @Test
    public void shouldDeleteBookWithId() throws Exception {
        presenter.deleteBook(0);
        verify(repo, only()).deleteBook(0);
    }

    @Test
    public void goToAddNewBook() throws Exception {
        presenter.addNewBook();
        verify(view, only()).goToAddNewBook();
    }

    @Test
    public void goToCurrency() throws Exception {
        presenter.toCurrency();
        verify(view, only()).goToCurrency();
    }

    @Test
    public void goToSettings() throws Exception {
        presenter.toSettings();
        verify(view, only()).goToSettings();
    }

    @Test
    public void shouldCloseBookRepo() throws Exception {
        presenter.close();
        verify(repo, only()).close();
    }
}