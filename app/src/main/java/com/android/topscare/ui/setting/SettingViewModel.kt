package com.android.topscare.ui.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class SettingViewModel @ViewModelInject constructor(

): BaseViewModel<Any>() {
    val _versoin_code = MutableLiveData<String>()
    val _onBackPressed = SingleLiveEvent<String>()
    val _onAboutPressed = SingleLiveEvent<String>()
    val _onUrlEndpointPressed = SingleLiveEvent<String>()
    val _onTutorialPressed = SingleLiveEvent<String>()
    val _onSoftwareUpdatePressed = SingleLiveEvent<String>()
    val _urlEndpoint = SingleLiveEvent<String>()
    init {
        _versoin_code.postValue("version 0.1.1")
        _urlEndpoint.postValue("10.127.0.0")
    }
    fun onAboutPressed(){
        _onAboutPressed()
    }
    fun onUrlEndpointPressed(){
        _onUrlEndpointPressed()
    }
    fun onTutorialPressed(){
        _onTutorialPressed()
    }
    fun onSoftwareUpdatePressed(){
        _onSoftwareUpdatePressed()
    }
    fun onBackPressed(){
        _onBackPressed()
    }
}