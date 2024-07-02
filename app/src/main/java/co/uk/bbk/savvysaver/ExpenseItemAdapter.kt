package co.uk.bbk.savvysaver


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * ExpenseItemAdapter is an adapter class for the RecyclerView in the breakdown activity.
 */

class ExpenseItemAdapter(val expenses: MutableList<Expense>) :
    RecyclerView.Adapter<ExpenseItemViewHolder>() {

    /**
     * Creates a new ViewHolder for the RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseItemViewHolder {
        return ExpenseItemViewHolder.create(parent)
    }

    /**
     * Binds the data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: ExpenseItemViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    /**
     * Returns the number of items in the list.
     */

    override fun getItemCount(): Int = expenses.size


    /**
     * Updates the list of expenses.
     */
    fun updateExpenses(newExpenses: List<Expense>) {
        expenses.clear()
        expenses.addAll(newExpenses)
        notifyDataSetChanged()

    }


    /**
     * Returns the total expenses.
     */
    fun getTotal(): String {
        return "Total expenses £${expenses.sumOf { it.amount }}"
    }

    /**
     * Returns the savings.
     */
    fun getSavings(currentBudget:Double) : String {
        val totalExpenses = expenses.sumOf { it.amount }
        val savings = currentBudget - totalExpenses
        return "Savings £$savings"
    }

}
