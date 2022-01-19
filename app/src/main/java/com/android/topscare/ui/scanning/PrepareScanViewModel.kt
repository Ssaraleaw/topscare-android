package com.android.topscare.ui.scanning

import androidx.hilt.lifecycle.ViewModelInject
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class PrepareScanViewModel @ViewModelInject constructor(

) : BaseViewModel<Unit>() {
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onCameraPressed = SingleLiveEvent<Any>()
    val _scanMode = SingleLiveEvent<ScanMode>()
    fun init(scanMode : ScanMode){
        _scanMode.value = scanMode
    }
    fun onBackPressed(){
        _onBackPressed()
    }
    fun onCameraPressed(){
        _onCameraPressed()
    }

    fun getProductByKey(key: String){

    }
}