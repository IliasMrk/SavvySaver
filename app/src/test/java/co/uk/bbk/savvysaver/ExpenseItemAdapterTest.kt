package co.uk.bbk.savvysaver

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@LargeTest
@RunWith(AndroidJUnit4::class)
class ExpenseItemAdapterTest {

    // The adapter we are going to test
    private lateinit var adapter: ExpenseItemAdapter

    // Set up
    @Before
    fun setUp() {

        MockitoAnnotations.openMocks(this)

        // a list of expenses
        val expenses = mutableListOf(
            Expense(1, "Groceries", 150.0, "2024-08-01", "Shopping"),
            Expense(2, "Rent", 900.0, "2024-08-04", "Utilities"),
            Expense(3, "Gym", 50.0, "2024-09-01", "Entertainment")
        )

        // initializing the adapter with expenses
        adapter = ExpenseItemAdapter(expenses)
    }

    // Test get item count
    @Test
    fun testGetItemCount() {
        // checking if number of items is correct
        assertEquals(3, adapter.itemCount)
    }

    // Test updating the list of expenses
    @Test
    fun testUpdateExpenses() {
        // a list of expenses
        val newExpenses = listOf(
            Expense(4, "Transport", 150.0, "2024-09-01", "Other"),
            Expense(5, "Electricity", 120.0, "2024-08-01", "Utilities")
        )
        // update adapter with the new expenses
        adapter.updateExpenses(newExpenses)
        // check if number of items is correct
        assertEquals(2, adapter.itemCount)
        // check if the first item is the new one
        assertEquals(newExpenses[0], adapter.expenses[0])
    }

    // Test getting the total expenses
    @Test
    fun testGetTotal() {
        // check if the total expenses are correct
        val total = adapter.getTotal()
        assertEquals("Total expenses £1270.0", total)
    }

    // Test getting the savings
    @Test
    fun testGetSavings() {
        // check if savings are correct with a budget of 2000
        val savings = adapter.getSavings(2000.0)
        assertEquals("Savings £730.0", savings)
    }
}
