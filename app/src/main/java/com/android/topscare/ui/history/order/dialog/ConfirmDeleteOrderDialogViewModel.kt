package com.android.topscare.ui.history.order.dialog

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class ConfirmDeleteOrderDialogViewModel @ViewModelInject constructor(
): BaseViewModel<Unit>() {
    val _title = MutableLiveData<String>()
    val _desc = MutableLiveData<String>()
    val _onConfirmPressed = SingleLiveEvent<Any>()
    val _onCancelPressed = SingleLiveEvent<Any>()

    fun init(title: String, desc: String){
        _title.value = title
        _desc.value = desc
    }
    fun onConfirmPressed(){
        _onConfirmPressed()
    }
    fun onCancelPressed(){
        _onCancelPressed()
    }
}