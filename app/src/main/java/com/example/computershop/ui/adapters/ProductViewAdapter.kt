package com.example.computershop.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.computershop.databinding.ProductItemBinding
import com.example.computershop.network.data.models.responses.products.ProductData

private const val EXTENSION = ".jpg"
private const val RUB = "₽"


class ProductViewAdapter(private val action: (Int) -> Unit) :
    PagingDataAdapter<ProductData, ProductViewAdapter.ProductViewHolder>(DiffUtilCallBack()){

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductViewHolder {

        val productItemBinding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return ProductViewHolder(productItemBinding, action)
    }

    class ProductViewHolder(val binding: ProductItemBinding, private val action: (Int) -> Unit)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(data: ProductData) {
            binding.productTitle.text = data.title
            binding.productPrice.text = data.price.toString() + RUB
            val url = data.img + EXTENSION

            Glide.with(binding.productImg)
                .load(url)
                .timeout(6000000)
                .into(binding.productImg)

            binding.root.setOnClickListener {
                action.invoke(absoluteAdapterPosition)
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