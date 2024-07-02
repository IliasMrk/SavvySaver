package co.uk.bbk.savvysaver


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.savvysaver.databinding.ActivityOptionsBinding


class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding
    private val budgetViewModel: BudgetViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("OptionsActivity", "onCreate")

        val budgetDao = ExpenseDatabase.getInstance(applicationContext).budgetDao()
        budgetViewModel.budgetDao = budgetDao
        Log.d("OptionsActivity", "BudgetDao initialized")

        // Observe the budget text
        budgetViewModel.budgetText.observe(this) { budgetText ->
            binding.textView.text = budgetText
            Log.d("OptionsActivity", "Budget Observing: $budgetText")
        }


        budgetViewModel.getBudget()



        //Button Listeners

        binding.updateBudgetButton.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java)
            startActivity(intent)
        }

        binding.viewButton.setOnClickListener {
            val intent = Intent(this, ViewActivity::class.java)
            startActivity(intent)
        }

        binding.breakdownButton.setOnClickListener {
            val intent = Intent(this, BreakdownActivity::class.java)
            startActivity(intent)
        }

        binding.exitButton.setOnClickListener {
            //exit the app
            finishAffinity()
        }


    }

    override fun onResume() {
        super.onResume()
        // Update the TextView with the current budget text
        val amount = budgetViewModel.getBudget()
        binding.textView.text = amount.toString()
    }

}