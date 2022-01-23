package com.android.topscare.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.android.topscare.databinding.ViewholderCountItemBinding
import com.android.topscare.domain.model.CountResponse

class PagedCountHistoryListAdapter(
    private val onItemClicked: (CountResponse) -> Unit
): PagedListAdapter<CountResponse, ProductCountHistoryViewHolder>(CountResponse.DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductCountHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductCountHistoryViewHolder(ViewholderCountItemBinding.inflate(layoutInflater, parent, false)){
            getItem(it)?.let { count ->
                onItemClicked(count)
            }
        }
    }

    override fun onBindViewHolder(holder: ProductCountHistoryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}