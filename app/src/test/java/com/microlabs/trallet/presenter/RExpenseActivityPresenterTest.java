package com.microlabs.trallet.presenter;

/**
 * Created by Hansen on 4/24/2017.
 *
 * Test for RExpense Activity Presenter
 */
public class RExpenseActivityPresenterTest {

//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Mock
//    public DatabaseBookRepository repo;
//
//    @Mock
//    public ExpenseActivityView view;
//
//    private ExpenseActivityPresenter presenter;
//
//    private List<RExpense> RExpenseList = Arrays.asList(new RExpense(), new RExpense(), new RExpense());
//
//    @Before
//    public void createExpenseActivityPresenter() {
//        presenter = new ExpenseActivityPresenter(view, repo);
//    }
//
//    /*@Test
//    public void shouldPass() {
//        Assert.assertEquals(1, 1);
//    }*/
//
//    @Test
//    public void shouldReturnListOfExpenseWithBookIdToView() {
//        when(repo.getExpenseList(0)).thenReturn(RExpenseList);
//        presenter.loadExpense(0);
//        verify(view).showExpenses(RExpenseList);
//    }
//
//    @Test
//    public void shouldReturnNullToView() {
//        when(repo.getExpenseList(0)).thenReturn(Collections.EMPTY_LIST);
//        presenter.loadExpense(0);
//        verify(view).showNoExpense(Collections.EMPTY_LIST);
//    }
//
//    @Test
//    public void shouldGoToAddNewExpense() {
//        presenter.addNewExpense();
//        verify(view).goToAddNewExpense(0);
//    }
//
//    @Test
//    public void shouldGoToEditExpense() {
//        presenter.editExpense(1);
//        verify(view).goToAddNewExpense(1);
//    }
//
//    @Test
//    public void shouldDeleteExpense() {
//        presenter.deleteExpense(1, 1);
//        verify(repo, only()).deleteExpense(1, 1);
//    }
//
//    @Test
//    public void shouldCloseBookRepo() {
//        presenter.close();
//        verify(repo, only()).close();
//    }
}