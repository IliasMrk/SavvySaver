package co.uk.bbk.savvysaver

import androidx.recyclerview.widget.DiffUtil

/**
 * CategoryTotalDiffCallback is a DiffUtil.ItemCallback implementation for CategoryTotal.
 */
class CategoryTotalDiffCallback : DiffUtil.ItemCallback<CategoryTotal>() {
    override fun areItemsTheSame(oldItem: CategoryTotal, newItem: CategoryTotal): Boolean {
        return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(oldItem: CategoryTotal, newItem: CategoryTotal): Boolean {
        return oldItem == newItem
    }
}
