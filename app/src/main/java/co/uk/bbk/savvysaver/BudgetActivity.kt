package co.uk.bbk.savvysaver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.savvysaver.databinding.ActivityBudgetBinding


class BudgetActivity : AppCompatActivity() {
    public lateinit var binding: ActivityBudgetBinding
    private val budgetViewModel: BudgetViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val budgetDao = ExpenseDatabase.getInstance(applicationContext).budgetDao()
        budgetViewModel.budgetDao = budgetDao



        // Observe the budget text
        budgetViewModel.budgetText.observe(this) { budgetText ->
            binding.textView.text = budgetText
            Log.d("BudgetActivity", "Budget Observing: $budgetText")
        }

        budgetViewModel.getBudget()

        //Button Listeners
        // Set the budget button click listener using toast to notify the user for invalid input
        //references: https://developer.android.com/guide/topics/ui/notifiers/toasts
        binding.setBudgetButton.setOnClickListener {
            val inputText = binding.textInputEditText.text.toString()
            try {
                val inputBudget = Budget(1, inputText.toDouble())
                budgetViewModel.setBudget(inputBudget)
                Log.d("BudgetActivity", "Budget Set: ${inputBudget.value}")
                finish()
            } catch (e: NumberFormatException) {
                // Show a toast message if the input is invalid
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()

                // Clear the input field
                binding.textInputEditText.text?.clear()
            }
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
    }
}
