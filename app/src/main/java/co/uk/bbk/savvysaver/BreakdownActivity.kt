package co.uk.bbk.savvysaver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.savvysaver.databinding.ActivityBreakdownBinding


class BreakdownActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreakdownBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val budgetViewModel: BudgetViewModel by viewModels()
    private lateinit var adapter: ExpenseItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreakdownBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("BreakdownActivity", "onCreate called")


        //initializing the DAO
        val dao = ExpenseDatabase.getInstance(applicationContext).expenseDao()
        expenseViewModel.expenseDao = dao
        val budgetDao = ExpenseDatabase.getInstance(applicationContext).budgetDao()
        budgetViewModel.budgetDao = budgetDao

        // Initialize the RecyclerView and Adapter
        adapter = ExpenseItemAdapter(mutableListOf())
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = adapter


        // Observe the budget text
        budgetViewModel.budgetText.observe(this) { budgetText ->
            binding.budgetView.text = budgetText
            Log.d("BudgetActivity", "Budget Observing: $budgetText")
        }

        // Observe budget amount
        budgetViewModel.budgetAmount.observe(this) { budgetAmount ->
            Log.d("BudgetActivity", "Budget Amount Observing: $budgetAmount")
            //update the savings
            updateSavings(budgetAmount)
        }

        // Observe expenses
        expenseViewModel.expenses.observe(this) { expenses ->
            Log.d("BreakdownActivity", "Expenses Observing: $expenses")
            if (expenses != null) {
                adapter.updateExpenses(expenses)
                //update the viewHolders with the totals from expenses and the savings
                binding.expensesView.text = adapter.getTotal()
            } else {
                Log.d("BreakdownActivity", "Expenses are null")
            }
        }

        //call to function to read data from the model
        expenseViewModel.readAllExpensesFromToday()
        budgetViewModel.getBudget()



        //Button Listeners
        binding.ExpenseHistory.setOnClickListener{
            val intent = Intent(this, ExpenseHistoryActivity::class.java)
            startActivity(intent)

        }
        binding.AddExpense.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        binding.backButton.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        expenseViewModel.readAllExpensesFromToday()
        budgetViewModel.getBudget()
        // Ensure the savings view is updated when the activity resumes
        budgetViewModel.budgetAmount.value?.let {
            updateSavings(it)

        }
    }

    private fun updateSavings(budgetAmount: Double) {
        binding.savedView.text = adapter.getSavings(budgetAmount)
    }
}




