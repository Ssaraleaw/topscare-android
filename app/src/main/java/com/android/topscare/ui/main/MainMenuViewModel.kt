package com.android.topscare.ui.main

import android.content.Context
import android.provider.Settings
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.usecase.GetProductByKeyUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

class MainMenuViewModel @ViewModelInject constructor(
//    private val getProductByKeyUseCase: GetProductByKeyUseCase
//    @ApplicationContext private val context: Context
) : BaseViewModel<Any>() {
    val _deviceName = MutableLiveData<String>()
    init {
        _deviceName.postValue(getDeviceName())
    }

    private fun getDeviceName(): String{
        return ""//Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME) ?: Settings.Secure.getString(context.contentResolver, "bluetooth_name")
    }
}