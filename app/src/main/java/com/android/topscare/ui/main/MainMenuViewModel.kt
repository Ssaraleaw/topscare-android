package com.android.topscare.ui.main

import android.content.Context
import android.provider.Settings
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.usecase.AppSettingUseCase
import com.android.topscare.domain.usecase.GetProductByKeyUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.state.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext

class MainMenuViewModel @ViewModelInject constructor(
    private val appSettingUseCase: AppSettingUseCase
) : BaseViewModel<Any>() {
    val _deviceName = MutableLiveData<String>()
    val _onCheckPressed = SingleLiveEvent<Any>()
    val _onCountPressed = SingleLiveEvent<Any>()
    val _onOrderPressed = SingleLiveEvent<Any>()
    val _onReceivePressed = SingleLiveEvent<Any>()
    val _onSettingPressed = SingleLiveEvent<Any>()

    init {
        _deviceName.postValue(getDeviceName())
    }

    private fun getDeviceName(): String{
        return "HH-Name: "+appSettingUseCase.getHhName()
    }

    fun onCheckPressed(){
        _onCheckPressed()
    }
    fun onCountPressed(){
        _onCountPressed()
    }
    fun onOrderPressed(){
        _onOrderPressed()
    }
    fun onReceivePressed(){
        _onReceivePressed()
    }
    fun onSettingPressed(){
        _onSettingPressed()
    }
}