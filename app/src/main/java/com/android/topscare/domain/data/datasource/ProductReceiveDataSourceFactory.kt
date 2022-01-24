package com.android.topscare.domain.data.datasource

import androidx.paging.DataSource
import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ProductHistoryRequest
import com.android.topscare.domain.model.ReceiveHistoryResponse
import kotlinx.coroutines.CoroutineScope

class ProductReceiveDataSourceFactory(
    historyRequest : ProductHistoryRequest,
    repository: TopsCareRepository,
    coroutineScope: CoroutineScope
) : DataSource.Factory<ProductHistoryRequest, ReceiveHistoryResponse>() {
    private val productReceivePagingSource = ProductReceivePagingSource(
        historyRequest,
        repository,
        coroutineScope
    )
    val initialPageUiState = productReceivePagingSource.initialPageUiState
    override fun create(): DataSource<ProductHistoryRequest, ReceiveHistoryResponse> {
        return productReceivePagingSource
    }

}