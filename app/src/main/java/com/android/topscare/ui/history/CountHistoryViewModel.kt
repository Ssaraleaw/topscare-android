package com.android.topscare.ui.history

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.usecase.GetProductCountUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent
import com.android.topscare.lib_base.utils.PagedListResult

class CountHistoryViewModel @ViewModelInject constructor(
    private val getProductCountUseCase : GetProductCountUseCase
) : BaseViewModel<Unit>(){
    val _search = MutableLiveData<String>()
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onSearchPressed = SingleLiveEvent<Any>()

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
    private fun getCountHistory(){
        pagedListResult.value = getProductCountUseCase(viewModelScope,"", page = 0)
    }
}