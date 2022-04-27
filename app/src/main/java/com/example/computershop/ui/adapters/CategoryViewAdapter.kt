package com.example.computershop.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.computershop.R
import com.example.computershop.network.data.models.responses.categories.CategoryData

class CategoryViewAdapter: RecyclerView.Adapter<CategoryViewAdapter.CatalogViewHolder>() {

    private var categoryItems = ArrayList<CategoryData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdateData(categoryItems: ArrayList<CategoryData>){
        this.categoryItems = categoryItems
        notifyDataSetChanged()
    }

    class CatalogViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val categoryTitle: TextView = view.findViewById(R.id.categoryTitle)
        private val categoryInfo: TextView = view.findViewById(R.id.categoryInfo)

        fun bind(data: CategoryData) {
            categoryTitle.text = data.title
            categoryInfo.text = data.info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)

        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(categoryItems.get(position))
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }
}