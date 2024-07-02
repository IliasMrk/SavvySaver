package co.uk.bbk.savvysaver

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ViewModel class that holds the budget text
class BudgetViewModel : ViewModel() {
    private val _budget = MutableLiveData<Budget>()
    val budget: LiveData<Budget> get() = _budget

    private val _budgetText = MutableLiveData<String>()
    val budgetText: LiveData<String> get() = _budgetText

    val _budgetAmount = MutableLiveData<Double>()
    val budgetAmount: LiveData<Double> get() = _budgetAmount

    var budgetDao: BudgetDao? = null


    init{
        getBudget()
    }


    /**
     * Function that loads the budget from the database
     */
    fun getBudget() {
        viewModelScope.launch {
            budgetDao?.let {
                _budget.value = it.getBudget()
                Log.d("BudgetViewModel", "Budget was loaded: $budget")
                updateBudgetText(budget.value)
                _budgetAmount.value = budget.value?.value
            }

        }
    }
    /**
     * Function that sets the budget
     */
    fun setBudget(amount: Budget) {

        viewModelScope.launch {
            budgetDao?.let {
                it.insertBudget(amount)
                _budget.value = amount
                Log.d("BudgetViewModel", "Budget was updated: ${budget.value}")
                updateBudgetText(_budget.value)

            }
        }
    }

    fun getBudgetAmount():Double {
        return budgetAmount.value!!
    }

    /**
     * Function that updates the budget text
     */
     fun updateBudgetText(budget: Budget?) {
        if (budget == null) {
            _budgetText.value = "Please set your budget"
        } else {
            _budgetText.value = "Budget: £${budget.value}"
        }
    }



}
