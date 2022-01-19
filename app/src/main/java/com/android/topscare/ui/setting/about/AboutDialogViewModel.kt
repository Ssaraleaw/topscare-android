package com.android.topscare.ui.setting.about

import androidx.hilt.lifecycle.ViewModelInject
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class AboutDialogViewModel @ViewModelInject constructor(

): BaseViewModel<Unit>() {
    val _onClosePressed = SingleLiveEvent<Any>()
    fun onClosePressed(){
        _onClosePressed()
    }
}