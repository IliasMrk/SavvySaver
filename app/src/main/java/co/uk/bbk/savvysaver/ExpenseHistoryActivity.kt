package co.uk.bbk.savvysaver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.savvysaver.databinding.ActivityExpenseHistoryBinding


class ExpenseHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseHistoryBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var adapter: ExpenseItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ExpenseHistoryActivity", "onCreate called")


        //initializing the DAO
        val dao = ExpenseDatabase.getInstance(applicationContext).expenseDao()
        expenseViewModel.expenseDao = dao

        // Initialize the RecyclerView and Adapter
        adapter = ExpenseItemAdapter(mutableListOf())
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.expenseRecyclerView.adapter = adapter

        // Observe expenses
        expenseViewModel.groupedExpenses.observe(this) { groupedExpenses ->
            Log.d("ExpenseHistoryActivity", "Expenses Observing: $groupedExpenses")
            if (groupedExpenses != null) {
                adapter.updateExpenses(groupedExpenses)
            } else {
                Log.d("ExpenseHistoryActivity", "Expenses are null")
            }
        }

        //call to function to read data from the model
        expenseViewModel.getExpensesSorted()


        binding.AddExpense.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }




}
