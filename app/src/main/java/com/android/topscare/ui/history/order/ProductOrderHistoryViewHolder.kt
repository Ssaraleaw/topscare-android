package com.android.topscare.ui.history.order

import androidx.recyclerview.widget.RecyclerView
import com.android.topscare.databinding.ViewholderCountItemBinding
import com.android.topscare.databinding.ViewholderOrderItemBinding
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.lib_base.extension.underline

class ProductOrderHistoryViewHolder(
    private val binding: ViewholderOrderItemBinding,
    private val onItemClicked: (Int) -> Unit,
    private val onItemDeleteClicked: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.labelName.setOnClickListener {
            onItemClicked(bindingAdapterPosition)
        }
        binding.labelName.underline()
        binding.btnDelete.setOnClickListener{
            onItemDeleteClicked(bindingAdapterPosition)
        }
    }

    fun bind(orderHistoryResponse: OrderHistoryResponse) {
        binding.orderHistoryResponse = orderHistoryResponse
    }
}
