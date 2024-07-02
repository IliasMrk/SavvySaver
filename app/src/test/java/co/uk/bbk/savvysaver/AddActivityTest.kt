package co.uk.bbk.savvysaver


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.uk.bbk.savvysaver.databinding.ActivityAddExpenseBinding
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddActivityTest {

    // Mock ExpenseViewModel for interactions
    @Mock
    private lateinit var expenseViewModel: ExpenseViewModel

    // rule annotation
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(AddActivity::class.java)

    // Set up
    @Before
    fun setUp() {
        // initialize mock objects
        MockitoAnnotations.openMocks(this)
        // set mock ExpenseViewModel in activity using reflection
        activityScenarioRule.scenario.onActivity { activity ->
            val expenseViewModelField = AddActivity::class.java.getDeclaredField("expenseViewModel")
            expenseViewModelField.isAccessible = true
            expenseViewModelField.set(activity, expenseViewModel)
        }
    }

    // Test for addButton with valid input
    @Test
    fun testAddButton_withValidInput() {
        activityScenarioRule.scenario.onActivity { activity ->

            val binding = ActivityAddExpenseBinding.inflate(activity.layoutInflater)
            activity.setContentView(binding.root)

            // set valid values for the fields
            binding.ExpenseTitle.setText("Rent")
            binding.ExpenseDate.setText("2024-08-20")
            binding.ExpenseAmount.setText("900.0")
            binding.categorySpinner.setSelection(0) // Utilities positioned at 0

            // click the addButton
            binding.addButton.performClick()

            // verify insertExpense was called with Expense object
            Mockito.verify(expenseViewModel).insertExpense(Mockito.any(Expense::class.java))
        }
    }
}
