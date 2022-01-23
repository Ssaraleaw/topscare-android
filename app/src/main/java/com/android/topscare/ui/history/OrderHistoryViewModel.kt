package com.android.topscare.ui.history

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class OrderHistoryViewModel @ViewModelInject constructor(

) : BaseViewModel<Unit>(){
    val _search = MutableLiveData<String>()
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onSearchPressed = SingleLiveEvent<Any>()
    fun onBackPressed(){
        _onBackPressed()
    }
    fun onSearchPressed(){
        _onSearchPressed()
    }
}