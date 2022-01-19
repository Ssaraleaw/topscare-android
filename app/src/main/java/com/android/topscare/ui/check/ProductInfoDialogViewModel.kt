package com.android.topscare.ui.check

import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class ProductInfoDialogViewModel : BaseViewModel<Unit>() {
    val _onClosePressed = SingleLiveEvent<Any>()
    fun onClosePressed(){
        _onClosePressed()
    }
}