package com.android.topscare

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.domain.model.CountRequest
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class NavHostViewModel @ViewModelInject constructor(

): BaseViewModel<Unit>() {
    val scanMode = MutableLiveData<ScanMode>()
    val _doCount = SingleLiveEvent<CountRequest>()
    init {
        scanMode.value = ScanMode.CHECK
    }
}