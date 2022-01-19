package com.android.topscare.ui.setting.tutorial

import androidx.hilt.lifecycle.ViewModelInject
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class TutorialViewModel @ViewModelInject constructor(

): BaseViewModel<Any>() {
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onScanningProcessPressed = SingleLiveEvent<Any>()
    val _onPreparePressed = SingleLiveEvent<Any>()
    val _onEtcPressed = SingleLiveEvent<Any>()

    fun onBackPressed(){
        _onBackPressed()
    }
    fun onScanningProcessPressed(){
        _onScanningProcessPressed()
    }

    fun onPreparePressed(){
        _onPreparePressed()
    }

    fun onEtcPressed(){
        _onEtcPressed()
    }
}