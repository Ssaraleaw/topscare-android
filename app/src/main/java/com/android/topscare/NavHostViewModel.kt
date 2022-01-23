package com.android.topscare

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.domain.model.CountRequest
import com.android.topscare.domain.model.OrderRequest
import com.android.topscare.domain.model.ReceiveRequest
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class NavHostViewModel @ViewModelInject constructor(

): BaseViewModel<Unit>() {
    val scanMode = MutableLiveData<ScanMode>()
    val _doCount = SingleLiveEvent<CountRequest>()
    val _doOrder = SingleLiveEvent<OrderRequest>()
    val _doReceive = SingleLiveEvent<ReceiveRequest>()
    val _onUpdateUrlSuccessEvent = SingleLiveEvent<Any>()
    init {
        scanMode.value = ScanMode.CHECK
    }
}