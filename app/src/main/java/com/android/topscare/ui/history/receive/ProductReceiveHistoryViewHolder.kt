package com.android.topscare.ui.history.receive

import androidx.recyclerview.widget.RecyclerView
import com.android.topscare.R
import com.android.topscare.databinding.ViewholderCountItemBinding
import com.android.topscare.databinding.ViewholderOrderItemBinding
import com.android.topscare.databinding.ViewholderReceiveItemBinding
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ReceiveHistoryResponse
import com.android.topscare.lib_base.extension.toDateFormat
import com.android.topscare.lib_base.extension.underline

class ProductReceiveHistoryViewHolder(
    private val binding: ViewholderReceiveItemBinding,
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

    fun bind(receiveHistoryResponse: ReceiveHistoryResponse) {
        binding.receiveHistoryResponse = receiveHistoryResponse
        receiveHistoryResponse?.let {
            binding.labelExp.text = binding.root.context.getString(R.string.fexpdate,
                it.expDate.toDateFormat())
        }
    }
}
