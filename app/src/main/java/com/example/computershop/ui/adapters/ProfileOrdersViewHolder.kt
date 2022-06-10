package com.example.computershop.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.computershop.databinding.ProfileOrderItemBinding
import com.example.computershop.network.data.models.responses.order.Data

class ProfileOrdersViewAdapter:
    RecyclerView.Adapter<ProfileOrdersViewAdapter.ProfileOrdersViewHolder>() {

    private var profileOrderItems = ArrayList<Data>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUpdateProfile(profileOrderItems: ArrayList<Data>) {
        this.profileOrderItems = profileOrderItems
        notifyDataSetChanged()
    }

    class ProfileOrdersViewHolder(val binding: ProfileOrderItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            val number = data.id
            binding.profileOrderNumber.text = "Заказ №$number"
            binding.profileOrderName.text = data.fio
            binding.profileOrderItemPrice.text = data.total_with_discount.toString()
            binding.profileOrderStatus.text = data.status
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileOrdersViewHolder {
        val profileOrdersItemBinding =
            ProfileOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ProfileOrdersViewHolder(profileOrdersItemBinding)
    }

    override fun onBindViewHolder(holder: ProfileOrdersViewHolder, position: Int) {
        holder.bind(profileOrderItems[position])
    }

    override fun getItemCount(): Int {
        return profileOrderItems.size
    }

}