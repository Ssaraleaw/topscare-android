package com.android.topscare

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.lib_base.base.BaseViewModel

class NavHostViewModel @ViewModelInject constructor(

): BaseViewModel<Unit>() {
    val scanMode = MutableLiveData<ScanMode>()
    init {
        scanMode.value = ScanMode.CHECK
    }
}