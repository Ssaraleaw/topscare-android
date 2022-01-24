package com.android.topscare.domain.data.datasource

import androidx.lifecycle.MediatorLiveData
import androidx.paging.PageKeyedDataSource
import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.ProductHistoryRequest
import com.android.topscare.lib_base.state.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductCountPagingSource (
    private val historyRequest : ProductHistoryRequest,
    private val repository: TopsCareRepository,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<ProductHistoryRequest, CountResponse>() {
    val initialPageUiState = MediatorLiveData<DataState<Unit>>()

    init {
        initialPageUiState.postValue(DataState.Success(Unit))
    }

    override fun loadInitial(
        params: LoadInitialParams<ProductHistoryRequest>,
        callback: LoadInitialCallback<ProductHistoryRequest, CountResponse>
    ) {
        initialPageUiState.postValue(DataState.Loading(Unit))
        coroutineScope.launch {
            repository.getProductCount(historyRequest.limit,historyRequest.page,historyRequest.key)?.let {
                callback.onResult(
                    it,
                    0,
                    it.size,
                    null,
                    historyRequest.copy(page = historyRequest.page + 1)
                )
                initialPageUiState.postValue(DataState.Success(Unit))
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<ProductHistoryRequest>,
        callback: LoadCallback<ProductHistoryRequest, CountResponse>
    ) {

    }

    override fun loadAfter(
        params: LoadParams<ProductHistoryRequest>,
        callback: LoadCallback<ProductHistoryRequest, CountResponse>
    ) {
        coroutineScope.launch {
            repository.getProductCount(historyRequest.limit,params.key.page,historyRequest.key)?.let {
                callback.onResult(
                    it,
                    params.key.copy(page = params.key.page + 1)
                )
            }
        }
    }
}