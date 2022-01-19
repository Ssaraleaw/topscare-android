package com.android.topscare.ui.error

import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class ErrorDialogViewModel : BaseViewModel<Unit>() {
    val _onClosePressed = SingleLiveEvent<Any>()
    fun onClosePressed(){
        _onClosePressed()
    }
}