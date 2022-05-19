package com.example.computershop.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.computershop.R
import com.example.computershop.network.data.models.responses.categories.CategoryData
import com.example.computershop.ui.listeners.OnCategoryItemClickListener

class CategoryViewAdapter: RecyclerView.Adapter<CategoryViewAdapter.CatalogViewHolder>() {

    var categoryItems = ArrayList<CategoryData>()
    private lateinit var categoryListener: OnCategoryItemClickListener

    fun setOnItemClickListener(listener: OnCategoryItemClickListener) {
        categoryListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdateCategory(categoryItems: ArrayList<CategoryData>){
        this.categoryItems = categoryItems
        notifyDataSetChanged()
    }

    class CatalogViewHolder(view: View, listener: OnCategoryItemClickListener): RecyclerView.ViewHolder(view){

        private val categoryTitle: TextView = view.findViewById(R.id.categoryTitle)

        fun bind(data: CategoryData) {
            categoryTitle.text = data.title
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)

        return CatalogViewHolder(view, categoryListener)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(categoryItems.get(position))
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }
}