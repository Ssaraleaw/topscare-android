package com.android.topscare.ui.history.count

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.DeleteCountRequest
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.usecase.DeleteCountProductUseCase
import com.android.topscare.domain.usecase.GetProductByKeyUseCase
import com.android.topscare.domain.usecase.GetProductCountUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent
import com.android.topscare.lib_base.utils.PagedListResult

class CountHistoryViewModel @ViewModelInject constructor(
    private val getProductCountUseCase : GetProductCountUseCase,
    private val getProductByKeyUseCase: GetProductByKeyUseCase,
    private val deleteCountProductUseCase: DeleteCountProductUseCase
) : BaseViewModel<Unit>(){
    val _search = MutableLiveData<String>()
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onSearchPressed = SingleLiveEvent<Any>()
    val _deleteSuccess = SingleLiveEvent<Any>()
    val _product = SingleLiveEvent<ProductResponse>()
    val _swipeRefresh = SingleLiveEvent<Any>()
    private val pagedListResult: MutableLiveData<PagedListResult<CountResponse>> = MutableLiveData()
    val productCountList = Transformations.switchMap(pagedListResult) { it.result }

    init {
        getCountHistory()
    }
    fun onBackPressed(){
        _onBackPressed()
    }
    fun onSearchPressed(){
        _onSearchPressed()
    }
    fun getCountHistory(){
        pagedListResult.value = getProductCountUseCase(viewModelScope,_search.value?:"", page = 0)
    }
    fun getProductByKey(key: String) = launchRequest {
        getProductByKeyUseCase(input = key)?.let {
            _product.postValue(it)
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }
    fun deleteCountProduct(id: String) = launchRequest {
        val data = deleteCountProductUseCase(
            DeleteCountRequest(id= id)
        )
        if(data){
            _deleteSuccess()
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }

    fun onSwipeRefresh() {
        getCountHistory()
        _swipeRefresh()
    }
}