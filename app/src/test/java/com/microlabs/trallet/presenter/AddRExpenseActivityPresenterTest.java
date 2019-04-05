package com.microlabs.trallet.presenter;

/**
 * Created by Hansen on 4/28/2017.
 *
 * Test for Add RExpense Activity Presenter
 */
public class AddRExpenseActivityPresenterTest {

//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();
//
//    @Mock
//    public AddExpenseView view;
//
//    @Mock
//    public DatabaseBookRepository repo;
//
//    private AddExpenseActivityPresenter presenter;
//
//    private RExpense RExpense = new RExpense();
//
//    @Before
//    public void setUp() throws Exception {
//        presenter = new AddExpenseActivityPresenter(view, repo);
//    }
//
//    /*@Test
//    public void shouldPass() throws Exception {
//        Assert.assertEquals(1, 1);
//    }*/
//
//    @Test
//    public void shouldFillInValues() throws Exception {
//        when(repo.getExpense(1)).thenReturn(RExpense);
//        presenter.getExpenseData(1);
//        verify(view).setupData(RExpense);
//    }
//
//    @Test
//    public void shouldFailToInsertNewExpense() throws Exception {
//        presenter.saveNewExpense("", 0, -1.0, 0, 0, null, "");
//        verify(view).showError();
//    }
//
//    @Test
//    public void shouldInsertNewExpense() throws Exception {
//        presenter.saveNewExpense("asdf", 1, 0.1, 1, 1, Calendar.getInstance().getTime(), "asdf");
//        verify(view).done();
//    }
//
//    @Test
//    public void shouldSetupCategory() throws Exception {
//        final List<RCategory> RCategoryList = Arrays.asList(new RCategory(), new RCategory(), new RCategory());
//        when(repo.getRCategoryList()).thenReturn(RCategoryList);
//        presenter.setupCategorySpinner();
//        verify(view).setupCategorySpinner(RCategoryList);
//    }
//
//    @Test
//    public void shouldSetupCurrency() throws Exception {
//        final List<RCurrency> RCurrencyList = Arrays.asList(new RCurrency(), new RCurrency(), new RCurrency());
//        when(repo.getRCurrencyList()).thenReturn(RCurrencyList);
//        presenter.setupCurrencySpinner();
//        verify(view).setupCurrencySpinner(RCurrencyList);
//    }
//
//    @Test
//    public void shouldUpdateExpense() throws Exception {
//        presenter.updateExpense(1, "asdf", 1, 0.1, 1, 1, Calendar.getInstance().getTime(), "asdf", 0.3);
//        verify(view).done();
//    }
//
//    @Test
//    public void shouldFailUpdateExpense() throws Exception {
//        presenter.updateExpense(0, "asdf", 1, 0.1, 1, 1, Calendar.getInstance().getTime(), "asdf", 0.3);
//        verify(view).showError();
//    }
}