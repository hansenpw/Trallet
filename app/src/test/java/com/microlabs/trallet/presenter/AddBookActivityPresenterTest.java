package com.microlabs.trallet.presenter;

import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddBookActivityView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hansen on 5/12/2017.
 *
 * Add Book Activity Presenter Test
 */
public class AddBookActivityPresenterTest {

    @Mock
    private AddBookActivityView view;

    @Mock
    private DatabaseBookRepository repo;

    private AddBookActivityPresenter presenter;
    private Book book;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new AddBookActivityPresenter(view, repo);
    }

    @Test
    public void shouldInsertNewBookToRepo() throws Exception {
        presenter.insertNewBook("Title", "desc");
        verify(view).done();
    }

    @Test
    public void shouldFailToInsertNewBookToRepo() throws Exception {
        presenter.insertNewBook("", "");
        verify(view).showError();
    }

    @Test
    public void shouldFillInValues() throws Exception {
        book = new Book();
        when(repo.getBook(1)).thenReturn(book);
        presenter.getBookData(1);
        verify(view).showBook(book);
    }

    @Test
    public void shouldUpdateBookToRepo() throws Exception {
        presenter.updateBook(1, "Title", "desc");
        verify(view).done();
    }

    @Test
    public void shouldFailUpdateBookToRepo() throws Exception {
        presenter.updateBook(0, "", "");
        verify(view).showError();
    }
}