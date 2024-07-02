package co.uk.bbk.savvysaver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.savvysaver.databinding.ActivityUpdateBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var adapter: ExpenseItemAdapter
    // Date format to display the date selected in the date picker
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize DAO
        val dao = ExpenseDatabase.getInstance(applicationContext).expenseDao()
        expenseViewModel.expenseDao = dao

        // Initialize the adapter
        adapter = ExpenseItemAdapter(mutableListOf())

        //Get expense id from Intent
        val id = intent.getIntExtra("id", 0)

        // get expense by id from database
        expenseViewModel.getExpenseById(id)



        // observe expense
        expenseViewModel.expense.observe(this) { expense ->
            Log.d("UpdateActivity", "Expense Observing")
            if (expense != null) {

                binding.itemName.setText(expense.title)
                binding.itemPrice.setText(expense.amount.toString())
                binding.expenseDate.setText(expense.date)

            }
            }


        //Button Listeners
        // Click listener for the date field to show the date picker
        binding.expenseDate.setOnClickListener {
            showDatePickerDialog()
        }
        //Update button click listener using toast to notify the user for invalid inputs
        //references: https://developer.android.com/guide/topics/ui/notifiers/toasts
        binding.updateItemButton.setOnClickListener {
            Log.d("UpdateActivity", "Update button clicked")

            val title = binding.itemName.text.toString()
            Log.d("UpdateActivity", "Title: $title")

            val date = binding.expenseDate.text.toString()
            Log.d("UpdateActivity", "Date: $date")

            val category = binding.categorySpinner.selectedItem.toString()
            Log.d("UpdateActivity", "Category: $category")

            val amountText = binding.itemPrice.text.toString()
            val amount = amountText.toDoubleOrNull()
            Log.d("UpdateActivity", "Amount: $amount")

            if (title.any { !it.isLetter() && !it.isWhitespace() }) {
                Toast.makeText(this, "Title should contain only letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (date.isEmpty()) {
                Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (amount == null) {
                Toast.makeText(this, "Please enter a valid number for the amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val updatedExpense = Expense(id = id, title = title, amount = amount, date = date, category = category)
                expenseViewModel.updateExpense(updatedExpense)
                adapter.notifyDataSetChanged()
                finish()
            } catch (e: Exception) {
                Log.e("UpdateActivity", "Exception occurred: ${e.message}")
                Toast.makeText(this, "An error occurred while updating the expense", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
        binding.deleteButton.setOnClickListener {

            // call to function to get the expense values from the text fields
            val expenseToDelete = getExpenseValues(id)
            expenseViewModel.deleteExpense(expenseToDelete)
            //notify the adapter that the data has changed
            adapter.notifyDataSetChanged()
            finish()

            }

        binding.backButton.setOnClickListener {
            finish()
        }


    }

    /**
     * Function to get the values from the text fields
     */
    private fun getExpenseValues(id : Int): Expense {
        val title = binding.itemName.text.toString()
        val amount = binding.itemPrice.text.toString().toDouble()
        val date = binding.expenseDate.text.toString()
        val category = binding.categorySpinner.selectedItem.toString()

        return Expense(id, title, amount, date, category)
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
            binding.expenseDate.setText(dateFormat.format(selectedDate))
        }
        // Show the date picker dialog
        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }


}