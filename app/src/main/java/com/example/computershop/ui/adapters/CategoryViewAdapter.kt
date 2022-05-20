package com.example.computershop.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.computershop.databinding.CategoryItemBinding
import com.example.computershop.network.data.models.responses.categories.CategoryData

class CategoryViewAdapter(val action: (Int) -> Unit) :
    RecyclerView.Adapter<CategoryViewAdapter.CatalogViewHolder>() {

    private var categoryItems = ArrayList<CategoryData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdateCategory(categoryItems: ArrayList<CategoryData>) {
        this.categoryItems = categoryItems
        notifyDataSetChanged()
    }

    class CatalogViewHolder(val binding: CategoryItemBinding, private val action: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryData) {
            binding.categoryTitle.text = data.title
            binding.root.setOnClickListener {
                action.invoke(data.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val categoryItemBinding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return CatalogViewHolder(categoryItemBinding, action)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(categoryItems[position])
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }
}