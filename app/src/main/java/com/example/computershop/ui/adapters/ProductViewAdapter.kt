package com.example.computershop.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.computershop.R
import com.example.computershop.network.data.models.responses.products.ProductData
import com.squareup.picasso.Picasso

private const val EXT = ".jpg"


class ProductViewAdapter :
    PagingDataAdapter<ProductData, ProductViewAdapter.ProductViewHolder>(DiffUtilCallBack()){

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductViewHolder {

        val inflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(inflater)
    }

    class ProductViewHolder(view: View): RecyclerView.ViewHolder(view){

        val productImage: ImageView = view.findViewById(R.id.productImg)
        val productTitle: TextView = view.findViewById(R.id.productTitle)
        val productPrice: TextView = view.findViewById(R.id.productPrice)

        fun bind(data: ProductData) {
            productTitle.text = data.title
            productPrice.text = data.price.toString()

            val url = data.img + EXT

            Picasso.get()
                .load(url)
                .into(productImage)
        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<ProductData>() {

        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.price == newItem.price
        }

    }

}