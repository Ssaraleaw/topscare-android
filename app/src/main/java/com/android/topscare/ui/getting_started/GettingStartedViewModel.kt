package com.android.topscare.ui.getting_started

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.model.CheckIpRequest
import com.android.topscare.domain.usecase.AppSettingUseCase
import com.android.topscare.domain.usecase.CheckIpUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent

class GettingStartedViewModel @ViewModelInject constructor(
    private val appSettingUseCase : AppSettingUseCase,
    private val checkIpUseCase : CheckIpUseCase
): BaseViewModel<Any>() {
    val _scanNow = SingleLiveEvent<Any>()
    val _checkIpSuccess = SingleLiveEvent<Any>()
    val _checkIpError = SingleLiveEvent<Any>()
    val _ip = MutableLiveData<String>()
    fun onStartNow(){
        _scanNow()
    }
    fun isFistTimeUsage() : Boolean{
        return appSettingUseCase.getBaseUrl().equals("http://localhost")
    }

    fun doCheckIp(ip: String, key: String) = launchRequest{
        _ip.value = "http://$ip"
        appSettingUseCase.saveBaseUrl("http://$ip")
        val data = checkIpUseCase(
            CheckIpRequest(ip = ip, key =key)
        )
        if(data){
            _checkIpSuccess()
        }else{
            _checkIpError()
            appSettingUseCase.saveBaseUrl("http://localhost")
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }
}