package com.android.topscare.ui.setting.url_endpoint

import android.net.InetAddresses
import android.os.Build
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.usecase.AppSettingUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent

class EditUrlEndpointDialogViewModel @ViewModelInject constructor(
    private val appSettingUseCase : AppSettingUseCase
): BaseViewModel<Unit>() {
    val _onClosePressed = SingleLiveEvent<Any>()
    val _onSavePressed = SingleLiveEvent<Any>()
    val _urlUiState = MediatorLiveData<DataState<Unit>>()
    val _url = MutableLiveData<String>()
    init {
        _url.postValue(appSettingUseCase.getBaseUrl())
    }
    fun onClosePressed(){
        _onClosePressed()
    }
    fun onSavePressed(){
        _onSavePressed()
    }
    fun doSaveUrl(){
        _url.value?.let {
            appSettingUseCase.saveBaseUrl(it)
        }
    }

}