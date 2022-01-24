package com.android.topscare.ui.history.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.android.topscare.databinding.ViewholderCountItemBinding
import com.android.topscare.databinding.ViewholderOrderItemBinding
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.ui.history.count.ProductCountHistoryViewHolder

class PagedOrderHistoryListAdapter(
    private val onItemClicked: (OrderHistoryResponse) -> Unit,
    private val onItemDeleteClicked: (OrderHistoryResponse) -> Unit,
): PagedListAdapter<OrderHistoryResponse, ProductOrderHistoryViewHolder>(OrderHistoryResponse.DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductOrderHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductOrderHistoryViewHolder(
            ViewholderOrderItemBinding.inflate(layoutInflater, parent, false),
            this::onItemClicked,
            this::onItemDeleteClicked
        )
    }

    override fun onBindViewHolder(holder: ProductOrderHistoryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
    private fun onItemClicked(i: Int){
        getItem(i)?.let { count ->
            onItemClicked(count)
        }
    }
    private fun onItemDeleteClicked(i: Int): Boolean{
        getItem(i)?.let { count ->
            onItemDeleteClicked(count)
        }
        return true
    }
}