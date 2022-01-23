package com.android.topscare.domain.data

import androidx.lifecycle.MediatorLiveData
import androidx.paging.PageKeyedDataSource
import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.ProductCountRequest
import com.android.topscare.lib_base.state.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductCountPagingSource (
    private val request : ProductCountRequest,
    private val repository: TopsCareRepository,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<ProductCountRequest, CountResponse>() {
    val initialPageUiState = MediatorLiveData<DataState<Unit>>()

    init {
        initialPageUiState.postValue(DataState.Success(Unit))
    }

    override fun loadInitial(
        params: LoadInitialParams<ProductCountRequest>,
        callback: LoadInitialCallback<ProductCountRequest, CountResponse>
    ) {
        initialPageUiState.postValue(DataState.Loading(Unit))
        coroutineScope.launch {
            repository.getProductCount(request.limit,request.page,request.key)?.let {
                callback.onResult(
                    it,
                    0,
                    it.size,
                    null,
                    request.copy(page = request.page + 1)
                )
                initialPageUiState.postValue(DataState.Success(Unit))
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<ProductCountRequest>,
        callback: LoadCallback<ProductCountRequest, CountResponse>
    ) {

    }

    override fun loadAfter(
        params: LoadParams<ProductCountRequest>,
        callback: LoadCallback<ProductCountRequest, CountResponse>
    ) {
        coroutineScope.launch {
            repository.getProductCount(request.limit,params.key.page,request.key)?.let {
                callback.onResult(
                    it,
                    params.key.copy(page = params.key.page + 1)
                )
            }
        }
    }
}