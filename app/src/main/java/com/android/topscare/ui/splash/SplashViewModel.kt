package com.android.topscare.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import com.android.topscare.domain.usecase.AppSettingUseCase
import com.android.topscare.lib_base.base.BaseViewModel

class SplashViewModel @ViewModelInject constructor(
    private val appSettingUseCase : AppSettingUseCase
): BaseViewModel<Unit>() {
    fun isFistTimeUsage() : Boolean{
        return appSettingUseCase.getBaseUrl().equals("http://localhost")
    }
}