package com.example.computershop.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.computershop.R
import com.example.computershop.network.data.models.responses.products.ProductData
import com.squareup.picasso.Picasso

private const val EXT = ".jpg"

class ProductViewAdapter: RecyclerView.Adapter<ProductViewAdapter.ProductViewHolder>() {

    private var productItems = ArrayList<ProductData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdateProduct(productItems: ArrayList<ProductData>){
        this.productItems = productItems
        notifyDataSetChanged()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productItems.get(position))
    }

    override fun getItemCount(): Int {
        return productItems.size
    }
}