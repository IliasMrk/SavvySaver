package co.uk.bbk.savvysaver

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.uk.bbk.savvysaver.databinding.ActivityUpdateBinding
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@LargeTest
@RunWith(AndroidJUnit4::class)
class UpdateActivityTest {

    // Mock the ExpenseViewModel for interactions
    @Mock
    private lateinit var expenseViewModel: ExpenseViewModel

    // rule annotation
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(UpdateActivity::class.java)

    // Set up
    @Before
    fun setUp() {
        // initialize the mock objects
        MockitoAnnotations.openMocks(this)
        // set mock ExpenseViewModel in activity using reflection
        activityScenarioRule.scenario.onActivity { activity ->
            val expenseViewModelField = UpdateActivity::class.java.getDeclaredField("expenseViewModel")
            expenseViewModelField.isAccessible = true
            expenseViewModelField.set(activity, expenseViewModel)
        }
    }

    // Test UpdateButton with valid Input
    @Test
    fun testUpdateButton_withValidInput() {
        activityScenarioRule.scenario.onActivity { activity ->

            val binding = ActivityUpdateBinding.inflate(activity.layoutInflater)
            activity.setContentView(binding.root)

            // set valid values for the fields
            binding.itemName.setText("Rent")
            binding.expenseDate.setText("2024-12-31")
            binding.itemPrice.setText("1200.0")
            binding.categorySpinner.setSelection(0) // Utilities positioned at 0

            // click the updateItemButton
            binding.updateItemButton.performClick()

            // verify updateExpense was called with Expense object
            Mockito.verify(expenseViewModel).updateExpense(Mockito.any(Expense::class.java))
        }
    }

}
