package co.uk.bbk.savvysaver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.savvysaver.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val budgetViewModel: BudgetViewModel by viewModels()
    private lateinit var adapter: CategoryTotalAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ViewActivity", "onCreate called")


        //initializing the DAO
        val expenseDao = ExpenseDatabase.getInstance(applicationContext).expenseDao()
        expenseViewModel.expenseDao = expenseDao
        val budgetDao = ExpenseDatabase.getInstance(applicationContext).budgetDao()
        budgetViewModel.budgetDao = budgetDao


        //initializing the recyclerview and the adapter
        adapter = CategoryTotalAdapter()
        binding.categoryTotalRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.categoryTotalRecyclerView.adapter = adapter


        // Observe the budget text
        budgetViewModel.budgetText.observe(this) { budgetText ->
            binding.budgetView.text = budgetText
            Log.d("ViewActivity", "Budget Observing: $budgetText")
        }



        // observe category totals
        expenseViewModel.categoryTotals.observe(this) { categoryTotals ->
            Log.d("ViewActivity", "Category totals Observing: $categoryTotals")
            if (categoryTotals != null) {
                adapter.updateCategoryTotals(categoryTotals)
                binding.expensesView.text = adapter.getTotal()
            } else {
                Log.d("ViewActivity", "Category totals are null")
            }
        }

        // display the budget text
        val amount = budgetViewModel.getBudget()
        binding.budgetView.text = amount.toString()



        //Button Listeners
        binding.breakdownButton.setOnClickListener {
            val intent = Intent(this, BreakdownActivity::class.java)
            startActivity(intent)
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    override fun onResume(){
        super.onResume()
        // load data
        expenseViewModel.getCategoryTotals()


    }

}


