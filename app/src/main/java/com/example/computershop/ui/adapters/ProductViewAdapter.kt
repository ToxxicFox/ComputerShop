package com.example.computershop.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.computershop.R
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.ui.listeners.OnProductItemClickListener

private const val EXT = ".jpg"
private const val RUB = "₽"


class ProductViewAdapter :
    PagingDataAdapter<ProductData, ProductViewAdapter.ProductViewHolder>(DiffUtilCallBack()){

    private lateinit var productListener: OnProductItemClickListener

    fun setOnItemClickListener(listener: OnProductItemClickListener) {
        productListener = listener
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductViewHolder {

        val inflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(inflater, productListener)
    }

    class ProductViewHolder(view: View, listener: OnProductItemClickListener)
        : RecyclerView.ViewHolder(view){

        private val productImage: ImageView = view.findViewById(R.id.productImg)
        private val productTitle: TextView = view.findViewById(R.id.productTitle)
        private val productPrice: TextView = view.findViewById(R.id.productPrice)

        fun bind(data: ProductData) {
            productTitle.text = data.title
            productPrice.text = data.price.toString() + RUB
            val url = data.img + EXT

            Glide.with(productImage)
                .load(url)
                .timeout(6000000)
                .into(productImage)
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
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