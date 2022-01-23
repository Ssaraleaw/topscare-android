package com.android.topscare.ui.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.BuildConfig
import com.android.topscare.domain.usecase.AppSettingUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent

class SettingViewModel @ViewModelInject constructor(
    private val appSettingUseCase : AppSettingUseCase
): BaseViewModel<Any>() {
    val _versoin_code = MutableLiveData<String>()
    val _onBackPressed = SingleLiveEvent<String>()
    val _onAboutPressed = SingleLiveEvent<String>()
    val _onUrlEndpointPressed = SingleLiveEvent<String>()
    val _onTutorialPressed = SingleLiveEvent<String>()
    val _onSoftwareUpdatePressed = SingleLiveEvent<String>()
    val _urlEndpoint = SingleLiveEvent<String>()
    init {
        _versoin_code.postValue("version: ${BuildConfig.VERSION_NAME}")
        _urlEndpoint.postValue(appSettingUseCase.getBaseUrl())
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
    fun reloadUrlEndpoint(){
        _urlEndpoint.postValue(appSettingUseCase.getBaseUrl())
    }
}