package co.uk.bbk.savvysaver

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals

@LargeTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class BudgetViewModelTest {

    // Mock the BudgetDao for interactions
    @Mock
    private lateinit var budgetDao: BudgetDao

    private lateinit var budgetViewModel: BudgetViewModel

    // Set up
    @Before
    fun setUp() {
        // Initialize the mock objects
        MockitoAnnotations.openMocks(this)
        // Initialize ViewModel
        budgetViewModel = BudgetViewModel()
        //Set the Dao
        budgetViewModel.budgetDao = budgetDao
    }

    // Test for the get budget method
    @Test
    suspend fun testGetBudget() {
        //prepare a budget
        val budget = Budget(1, 100.0)
        // Set up DAO to return the budget
        Mockito.`when`(budgetDao.getBudget()).thenReturn(budget)

        // observe budget LiveData
        val observer = Observer<Budget> {}
        try {
            budgetViewModel.budget.observeForever(observer)
            // load the budget
            budgetViewModel.getBudget()

            // verify that loaded budget matches the prepared budget
            val loadedBudget = budgetViewModel.budget.value
            assertEquals(budget, loadedBudget)
        } finally {
            // Clean the observer
            budgetViewModel.budget.removeObserver(observer)
        }
    }

    // Test for the set budget method
    @Test
    suspend fun testSetBudget(){
        // prepare a  budget
        val budget = Budget(1, 150.0)

        // observe budget LiveData
        val observer = Observer<Budget> {}
        try {
            budgetViewModel.budget.observeForever(observer)
            //set the budget
            budgetViewModel.setBudget(budget)

            // verify that budget was updated
            val updatedBudget = budgetViewModel.budget.value
            assertEquals(budget, updatedBudget)
            // verify call to Dao's insertBudget
            Mockito.verify(budgetDao).insertBudget(budget)
        } finally {
            // clean the observer
            budgetViewModel.budget.removeObserver(observer)
        }
    }

    // Test for the update budget text with valid value
    @Test
    fun testUpdateBudgetText() {
        // prepare a budget
        val budget = Budget(1, 200.0)
        val expectedText = "Budget: £200.0"

        // observe budgetText LiveData
        val observer = Observer<String> {}
        try {
            budgetViewModel.budgetText.observeForever(observer)
            //update budget text
            budgetViewModel.updateBudgetText(budget)

            // verify that budget text matches with the expected
            val updatedText = budgetViewModel.budgetText.value
            assertEquals(expectedText, updatedText)
        } finally {
            // clean up the observer
            budgetViewModel.budgetText.removeObserver(observer)
        }
    }

    // Test for the update budget text with null value
    @Test
    fun testUpdateBudgetText_nullBudget() {
        val expectedText = "Please set your budget"

        // observe budgetText LiveData
        val observer = Observer<String> {}
        try {
            budgetViewModel.budgetText.observeForever(observer)
            //update budget text with null budget
            budgetViewModel.updateBudgetText(null)

            // verify that budget text matches with the expected text
            val updatedText = budgetViewModel.budgetText.value
            assertEquals(expectedText, updatedText)
        } finally {
            // clean the observer
            budgetViewModel.budgetText.removeObserver(observer)
        }
    }
}
