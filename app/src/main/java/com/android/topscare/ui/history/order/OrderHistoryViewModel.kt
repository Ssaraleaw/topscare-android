package com.android.topscare.ui.history.order

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.android.topscare.domain.model.DeleteRequest
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.usecase.DeleteCountProductUseCase
import com.android.topscare.domain.usecase.DeleteOrderProductUseCase
import com.android.topscare.domain.usecase.GetProductByKeyUseCase
import com.android.topscare.domain.usecase.GetProductOrderHistoryUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent
import com.android.topscare.lib_base.utils.PagedListResult

class OrderHistoryViewModel @ViewModelInject constructor(
    private val getProductOrderHistoryUseCase : GetProductOrderHistoryUseCase,
    private val getProductByKeyUseCase: GetProductByKeyUseCase,
    private val deleteOrderProductUseCase: DeleteOrderProductUseCase
) : BaseViewModel<Unit>(){
    val _search = MutableLiveData<String>()
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onSearchPressed = SingleLiveEvent<Any>()
    val _deleteSuccess = SingleLiveEvent<Any>()
    val _product = SingleLiveEvent<ProductResponse>()
    val _swipeRefresh = SingleLiveEvent<Any>()
    private val pagedListResult: MutableLiveData<PagedListResult<OrderHistoryResponse>> = MutableLiveData()
    val productOrderList = Transformations.switchMap(pagedListResult) { it.result }

    init {
        getOrderHistory()
    }
    fun onBackPressed(){
        _onBackPressed()
    }
    fun onSearchPressed(){
        _onSearchPressed()
    }
    fun getOrderHistory(){
        pagedListResult.value = getProductOrderHistoryUseCase(viewModelScope,_search.value?:"", page = 0)
    }
    fun getProductByKey(key: String) = launchRequest {
        getProductByKeyUseCase(input = key)?.let {
            _product.postValue(it)
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }
    fun deleteOrderProduct(id: String) = launchRequest {
        val data = deleteOrderProductUseCase(
            DeleteRequest(id= id)
        )
        if(data){
            _deleteSuccess()
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }

    fun onSwipeRefresh() {
        getOrderHistory()
        _swipeRefresh()
    }
}