package com.android.topscare.domain.data.datasource

import androidx.paging.DataSource
import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.ProductHistoryRequest
import kotlinx.coroutines.CoroutineScope

class ProductCountDataSourceFactory(
    historyRequest : ProductHistoryRequest,
    repository: TopsCareRepository,
    coroutineScope: CoroutineScope
) : DataSource.Factory<ProductHistoryRequest,CountResponse>() {
    private val productCountPagingSource = ProductCountPagingSource(
        historyRequest,
        repository,
        coroutineScope
    )
    val initialPageUiState = productCountPagingSource.initialPageUiState
    override fun create(): DataSource<ProductHistoryRequest, CountResponse> {
        return productCountPagingSource
    }

}