package com.android.topscare.domain.data.datasource

import androidx.paging.DataSource
import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ProductHistoryRequest
import kotlinx.coroutines.CoroutineScope

class ProductOrderDataSourceFactory(
    historyRequest : ProductHistoryRequest,
    repository: TopsCareRepository,
    coroutineScope: CoroutineScope
) : DataSource.Factory<ProductHistoryRequest,OrderHistoryResponse>() {
    private val productOrderPagingSource = ProductOrderPagingSource(
        historyRequest,
        repository,
        coroutineScope
    )
    val initialPageUiState = productOrderPagingSource.initialPageUiState
    override fun create(): DataSource<ProductHistoryRequest, OrderHistoryResponse> {
        return productOrderPagingSource
    }

}