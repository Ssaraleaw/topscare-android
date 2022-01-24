package com.android.topscare.ui.history.receive

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.android.topscare.domain.model.DeleteRequest
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.model.ReceiveHistoryResponse
import com.android.topscare.domain.usecase.*
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent
import com.android.topscare.lib_base.utils.PagedListResult

class ReceiveHistoryViewModel @ViewModelInject constructor(
    private val getProductReceiveHistoryUseCase : GetProductReceiveHistoryUseCase,
    private val getProductByKeyUseCase: GetProductByKeyUseCase,
    private val deleteReceiveProductUseCase: DeleteReceiveProductUseCase
) : BaseViewModel<Unit>(){
    val _search = MutableLiveData<String>()
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onSearchPressed = SingleLiveEvent<Any>()
    val _deleteSuccess = SingleLiveEvent<Any>()
    val _product = SingleLiveEvent<ProductResponse>()
    val _swipeRefresh = SingleLiveEvent<Any>()
    private val pagedListResult: MutableLiveData<PagedListResult<ReceiveHistoryResponse>> = MutableLiveData()
    val productReceiveList = Transformations.switchMap(pagedListResult) { it.result }

    init {
        getReceiveHistory()
    }
    fun onBackPressed(){
        _onBackPressed()
    }
    fun onSearchPressed(){
        _onSearchPressed()
    }
    fun getReceiveHistory(){
        pagedListResult.value = getProductReceiveHistoryUseCase(viewModelScope,_search.value?:"", page = 0)
    }
    fun getProductByKey(key: String) = launchRequest {
        getProductByKeyUseCase(input = key)?.let {
            _product.postValue(it)
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }
    fun deleteOrderProduct(id: String) = launchRequest {
        val data = deleteReceiveProductUseCase(
            DeleteRequest(id= id)
        )
        if(data){
            _deleteSuccess()
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }

    fun onSwipeRefresh() {
        getReceiveHistory()
        _swipeRefresh()
    }
}