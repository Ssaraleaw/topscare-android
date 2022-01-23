package com.android.topscare.ui.history

import androidx.recyclerview.widget.RecyclerView
import com.android.topscare.databinding.ViewholderCountItemBinding
import com.android.topscare.domain.model.CountResponse

class ProductCountHistoryViewHolder(
    private val binding: ViewholderCountItemBinding,
    private val onItemClicked: (Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClicked(0)
        }
    }

    fun bind(countResponse: CountResponse) {
        binding.countResponse = countResponse
    }
}
