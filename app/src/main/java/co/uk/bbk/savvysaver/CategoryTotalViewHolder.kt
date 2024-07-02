package co.uk.bbk.savvysaver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.savvysaver.databinding.ItemCategoryTotalBinding

/**
 * CategoryTotalViewHolder is a ViewHolder that displays a category and its total.
 */
class CategoryTotalViewHolder(private val binding: ItemCategoryTotalBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(categoryTotal: CategoryTotal) {
        binding.categoryName.text = categoryTotal.category
        binding.categoryTotal.text = categoryTotal.total.toString()
    }

    companion object {
        fun create(parent: ViewGroup): CategoryTotalViewHolder {
            val binding = ItemCategoryTotalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CategoryTotalViewHolder(binding)
        }
    }
}
