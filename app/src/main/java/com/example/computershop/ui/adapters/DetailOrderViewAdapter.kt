package com.example.computershop.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.computershop.databinding.DetailOrderProductItemBinding
import com.example.computershop.network.data.models.responses.order.Item

class DetailOrderViewAdapter:
    RecyclerView.Adapter<DetailOrderViewAdapter.DetailOrderViewHolder>() {

    private var orderItems = ArrayList<Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdateCart(orderItems: ArrayList<Item>) {
        this.orderItems = orderItems
        notifyDataSetChanged()
    }

    class DetailOrderViewHolder(val binding: DetailOrderProductItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Item) {
            binding.titleOfOrderItem.text = data.product.title
            binding.categoryOfOrderItem.text = data.product.category.title
            binding.quantityOfOrderItem.text = data.quantity.toString()
            binding.cartOrderItemPrice.text = data.product.price.toString()
            Glide.with(binding.imageOfOrderItem)
                .load(data.product.img + EXTENSION)
                .timeout(6000000)
                .into(binding.imageOfOrderItem)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailOrderViewHolder {
        val detailOrderItemBinding =
            DetailOrderProductItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return DetailOrderViewHolder(detailOrderItemBinding)
    }

    override fun onBindViewHolder(holder: DetailOrderViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    override fun getItemCount(): Int {
        return orderItems.size
    }

    companion object {
        const val EXTENSION = ".jpg"
    }

}