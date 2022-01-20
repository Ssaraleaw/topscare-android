package com.android.topscare.ui.scanning

import android.content.Context
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.R
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.domain.model.CountRequest
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.usecase.AppSettingUseCase
import com.android.topscare.domain.usecase.GetProductByKeyUseCase
import com.android.topscare.domain.usecase.InsertCountProductUseCase
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.base.Event
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext

class PrepareScanViewModel @ViewModelInject constructor(
    private val getProductByKeyUseCase: GetProductByKeyUseCase,
    private val insertCountProductUseCase : InsertCountProductUseCase,
    private val appSettingUseCase: AppSettingUseCase,
    @ApplicationContext private val context: Context
) : BaseViewModel<Unit>() {
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onCameraPressed = SingleLiveEvent<Any>()
    val _scanMode = SingleLiveEvent<ScanMode>()
    val _product = SingleLiveEvent<ProductResponse>()
    val _title = MutableLiveData<String>()
    fun init(scanMode : ScanMode){
        _scanMode.value = scanMode
        when(scanMode){
            ScanMode.CHECK ->{
                _title.value = context.getString(R.string.check)
            }
            ScanMode.ORDER ->{
                _title.value = context.getString(R.string.order)
            }
            ScanMode.RECEIVE ->{
                _title.value = context.getString(R.string.receive)
            }
            ScanMode.COUNT ->{
                _title.value = context.getString(R.string.count)
            }
        }
    }
    fun onBackPressed(){
        _onBackPressed()
    }
    fun onCameraPressed(){
        _onCameraPressed()
    }

    fun getProductByKey(key: String) = launchRequest {
        getProductByKeyUseCase(input = key)?.let {
            _product.postValue(it)
        }
        _dataStates.postValue(DataState.Success(getViewState()))
    }

    fun insertCount(request: CountRequest) = launchRequest{
        val data = insertCountProductUseCase(
            CountRequest(request.id, request.amount, appSettingUseCase.getHhName())
        )
        _dataStates.postValue(DataState.Success(getViewState()))
        if(!data){
            _dataStates.value = DataState.Error(Unit, Event(Exception("")))
        }else{
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}