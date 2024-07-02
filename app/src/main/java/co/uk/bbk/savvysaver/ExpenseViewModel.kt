package co.uk.bbk.savvysaver


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * ViewModel for the Expense
 */
class ExpenseViewModel: ViewModel() {


    var expenseDao: ExpenseDao? = null

    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> get() = _expenses

    private val _categoryTotals = MutableLiveData<List<CategoryTotal>>()
    val categoryTotals: LiveData<List<CategoryTotal>> get() = _categoryTotals

    private val _expense = MutableLiveData<Expense>()
    val expense: LiveData<Expense> get() = _expense

    private val _groupedExpenses = MutableLiveData<List<Expense>>()
    val groupedExpenses: LiveData<List<Expense>> get() = _groupedExpenses


    init {
        readAllExpenses()
        getCategoryTotals()
    }


    /**
     * Function that reads all expenses from the database
     * and updates the expenses LiveData
     */
    fun readAllExpenses() {
        viewModelScope.launch {
            expenseDao?.let {
                _expenses.value = it.getAllExpenses()
                Log.d("ExpenseViewModel", "All expenses loaded: $expenses")
            }
        }
    }

    /**
     * Function that reads all expenses from today
     * and updates the expenses LiveData
     */
    fun readAllExpensesFromToday() {
        viewModelScope.launch {
            expenseDao?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val todayDate = dateFormat.format(Date())
                _expenses.value = it.getExpensesFromToday(todayDate)
                Log.d("ExpenseViewModel", "All expenses from today loaded: $expenses")
            }
        }
    }

    fun getExpensesSorted(){
        viewModelScope.launch {
            expenseDao?.let{
                _groupedExpenses.value = it.getExpensesByDate()

            }
        }
    }

    /**
     * Function that reads the category totals
     * from the database
     */

    fun getCategoryTotals() {
        viewModelScope.launch {
            expenseDao?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val todayDate = dateFormat.format(Date())
                _categoryTotals.value = it.getTotalByCategory(todayDate)
                Log.d("ExpenseViewModel", "Category totals loaded: $categoryTotals")
            }
        }
    }


    /**
     * Function that inserts an expense into the database
     */
    fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao?.let {
                it.addExpense(expense)
                readAllExpenses()
                Log.d("ExpenseViewModel", "Expense added: $expense")
            }
        }
    }

    /**
     * Function that updates an expense in the database
     */
    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao?.updateExpense(expense)

            readAllExpenses()
        }
    }

    /**
     * Function that deletes an expense from the database
     */

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao?.deleteExpense(expense)
            readAllExpenses()
        }
    }

    fun getExpenseById(id: Int) {
        viewModelScope.launch {
            expenseDao?.let {
                _expense.value = it.getExpenseById(id)
            }


        }

    }
}
