package co.uk.bbk.savvysaver

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

/**
 * CategoryTotalAdapter is an adapter class for the RecyclerView in the view activity.
 */
class CategoryTotalAdapter :
    ListAdapter<CategoryTotal, CategoryTotalViewHolder>(CategoryTotalDiffCallback()) {

    /**
     * Creates a new ViewHolder for the RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTotalViewHolder {
        return CategoryTotalViewHolder.create(parent)
    }

    /**
     * Binds the data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: CategoryTotalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Updates the list of category totals.
     */
    fun updateCategoryTotals(newCategoryTotals: List<CategoryTotal>) {
        submitList(newCategoryTotals)
    }

    /**
     * Returns the total expenses for a category.
     */
    fun getTotal(): String {
        val total = currentList.sumOf { it.total }
        return "Total expenses £$total"
    }
}
