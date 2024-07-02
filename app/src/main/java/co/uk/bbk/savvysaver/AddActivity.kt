package co.uk.bbk.savvysaver


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import co.uk.bbk.savvysaver.databinding.ActivityAddExpenseBinding
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    // View binding instance for the activity's layout
    private lateinit var binding: ActivityAddExpenseBinding
    // Date format to display the date selected in the date picker
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val expenseViewModel: ExpenseViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DAO
        val dao = ExpenseDatabase.getInstance(applicationContext).expenseDao()
        expenseViewModel.expenseDao = dao



        // Button Listeners

        // Click listener for the date field to show the date picker
        binding.ExpenseDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Add expense button click listener using a toast to notify the user for invalid input
        //references: https://developer.android.com/guide/topics/ui/notifiers/toasts
        binding.addButton.setOnClickListener {
            // parameters for user input
            val title = binding.ExpenseTitle.text.toString()
            val date = binding.ExpenseDate.text.toString()
            val category = binding.categorySpinner.selectedItem.toString()

            // Check if title contains any numbers or symbols
            if (title.any { !it.isLetter() && !it.isWhitespace() }) {
                Toast.makeText(this, "Title should contain only letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the date field is filled
            if (date.isEmpty()) {
                Toast.makeText(this, "Please enter an expense date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val amount = binding.ExpenseAmount.text.toString().toDouble()

                // If all fields are valid, create a new expense
                val newExpense = Expense(title = title, amount = amount, date = date, category = category)
                Log.d("AddActivity", "Inserting expense: $newExpense")
                // Call function from view model
                expenseViewModel.insertExpense(newExpense)
                finish()
            } catch (e: NumberFormatException) {
                // Show a toast message if the amount is not a number
                Toast.makeText(this, "Please enter a number for the amount", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    /**Function to show the MaterialDatePicker dialog
     * references : https://abhiandroid.com/ui/datepicker#gsc.tab=0
    **/
    private fun showDatePickerDialog() {
        // Create a MaterialDatePicker instance
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
            .build()
        // Set the positive button click listener to handle the date selection
        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = Date(it)
            binding.ExpenseDate.setText(dateFormat.format(selectedDate))
        }
        // Show the date picker dialog
        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }
}
