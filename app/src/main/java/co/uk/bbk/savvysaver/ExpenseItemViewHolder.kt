package co.uk.bbk.savvysaver

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.savvysaver.databinding.ExpenseItemBinding

/**
 * A ViewHolder for the ExpenseItem
 */
class ExpenseItemViewHolder (
    private val binding: ExpenseItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Bind the ExpenseItem to the view and set the onClick listener
     */
    fun bind(expenseItem: Expense){
        binding.ExpenseTitle.text = expenseItem.title
        binding.ExpensePrice.text = expenseItem.amount.toString()
        binding.ExpenseDate.text = expenseItem.date
        binding.ExpenseCategory.text = expenseItem.category

        // RecyclerView onClick listener that opens the UpdateActivity and passes the data
        itemView.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", expenseItem.id)
            Log.d("ExpenseItemViewHolder", "The intent has processed the expense id")

            context.startActivity(intent)
        }
    }
    companion object{
        fun create(viewGroup: ViewGroup): ExpenseItemViewHolder{

            val layoutInflater = LayoutInflater.from(viewGroup.context)
            val binding = ExpenseItemBinding.inflate(layoutInflater, viewGroup, false)
            return ExpenseItemViewHolder(binding)
        }
    }



}
