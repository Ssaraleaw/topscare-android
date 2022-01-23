package com.android.topscare.domain.data

import androidx.paging.DataSource
import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.ProductCountRequest
import kotlinx.coroutines.CoroutineScope

class ProductCountDataSourceFactory(
    request : ProductCountRequest,
    repository: TopsCareRepository,
    coroutineScope: CoroutineScope
) : DataSource.Factory<ProductCountRequest,CountResponse>() {
    private val productCountPagingSource = ProductCountPagingSource(
        request,
        repository,
        coroutineScope
    )
    val initialPageUiState = productCountPagingSource.initialPageUiState
    override fun create(): DataSource<ProductCountRequest, CountResponse> {
        return productCountPagingSource
    }

}