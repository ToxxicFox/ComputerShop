package com.example.computershop.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.computershop.databinding.CartItemBinding
import com.example.computershop.network.data.models.responses.cart.CartData

class CartViewAdapter : RecyclerView.Adapter<CartViewAdapter.CartViewHolder>() {

    private var cartItems = ArrayList<CartData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdateCart(cartItems: ArrayList<CartData>) {
        this.cartItems = cartItems
        notifyDataSetChanged()
    }

    class CartViewHolder(val binding: CartItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: CartData) {
                binding.titleOfCartItem.text = data.product.title
                binding.categoryOfCartItem.text = data.product.category.title
                binding.quantityOfCartItem.text = data.quantity.toString()
                binding.cartItemPrice.text = data.product.price.toString()
                Glide.with(binding.imageOfCartItem)
                    .load(data.product.img + EXTENSION)
                    .timeout(6000000)
                    .into(binding.imageOfCartItem)
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val cartItemBinding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return CartViewHolder(cartItemBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    companion object {
        const val EXTENSION = ".jpg"
    }

}