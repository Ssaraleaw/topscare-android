package com.android.topscare.domain.usecase

import androidx.paging.LivePagedListBuilder
import com.android.topscare.domain.data.datasource.ProductCountDataSourceFactory
import com.android.topscare.domain.data.datasource.ProductOrderDataSourceFactory
import com.android.topscare.domain.data.datasource.ProductReceiveDataSourceFactory
import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ProductHistoryRequest
import com.android.topscare.domain.model.ReceiveHistoryResponse
import com.android.topscare.lib_base.utils.PagedListResult
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executors
import javax.inject.Inject

class GetProductReceiveHistoryUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    operator fun invoke(coroutineScope: CoroutineScope,key: String, page: Int) : PagedListResult<ReceiveHistoryResponse> {
        val dataSourceFactory = ProductReceiveDataSourceFactory(
            historyRequest = ProductHistoryRequest(key,page,LIMIT),
            repository = repository,
            coroutineScope = coroutineScope,
        )
        val result = LivePagedListBuilder(dataSourceFactory, Config.PagedList.config)
            .setFetchExecutor(Executors.newFixedThreadPool(3))
            .build()
        return PagedListResult(
            result,
            dataSourceFactory.initialPageUiState
        )
    }

    companion object {
        const val LIMIT = 10
    }
    object Config {
        object PagedList {
            val config = androidx.paging.PagedList.Config.Builder()
                .setInitialLoadSizeHint(4)
                .setEnablePlaceholders(false)
                .setPageSize(2)
                .setPrefetchDistance(2)
                .build()

            fun getPagedListConfig(
                initialSize: Int = 4,
                pagesSize: Int = 2,
                prefetchDistance: Int = 2
            ) = androidx.paging.PagedList.Config.Builder()
                .setInitialLoadSizeHint(initialSize)
                .setEnablePlaceholders(false)
                .setPageSize(pagesSize)
                .setPrefetchDistance(prefetchDistance)
                .build()
        }
    }
}