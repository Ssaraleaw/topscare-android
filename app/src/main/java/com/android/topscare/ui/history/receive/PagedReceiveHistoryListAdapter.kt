package com.android.topscare.ui.history.receive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.android.topscare.databinding.ViewholderCountItemBinding
import com.android.topscare.databinding.ViewholderOrderItemBinding
import com.android.topscare.databinding.ViewholderReceiveItemBinding
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ReceiveHistoryResponse
import com.android.topscare.ui.history.count.ProductCountHistoryViewHolder
import com.android.topscare.ui.history.order.ProductOrderHistoryViewHolder

class PagedReceiveHistoryListAdapter(
    private val onItemClicked: (ReceiveHistoryResponse) -> Unit,
    private val onItemDeleteClicked: (ReceiveHistoryResponse) -> Unit,
): PagedListAdapter<ReceiveHistoryResponse, ProductReceiveHistoryViewHolder>(ReceiveHistoryResponse.DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductReceiveHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductReceiveHistoryViewHolder(
            ViewholderReceiveItemBinding.inflate(layoutInflater, parent, false),
            this::onItemClicked,
            this::onItemDeleteClicked
        )
    }

    override fun onBindViewHolder(holder: ProductReceiveHistoryViewHolder, position: Int) {
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
        try {
            getItem(i)?.let { count ->
                onItemDeleteClicked(count)
            }
        }catch (ex: IndexOutOfBoundsException){
            ex.printStackTrace()
        }
        return true
    }
}