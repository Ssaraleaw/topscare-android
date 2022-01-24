package com.android.topscare.ui.history.count

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.android.topscare.databinding.ViewholderCountItemBinding
import com.android.topscare.domain.model.CountResponse

class PagedCountHistoryListAdapter(
    private val onItemClicked: (CountResponse) -> Unit,
    private val onItemDeleteClicked: (CountResponse) -> Unit,
): PagedListAdapter<CountResponse, ProductCountHistoryViewHolder>(CountResponse.DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductCountHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductCountHistoryViewHolder(
            ViewholderCountItemBinding.inflate(layoutInflater, parent, false),
            this::onItemClicked,
            this::onItemDeleteClicked
        )
    }

    override fun onBindViewHolder(holder: ProductCountHistoryViewHolder, position: Int) {
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