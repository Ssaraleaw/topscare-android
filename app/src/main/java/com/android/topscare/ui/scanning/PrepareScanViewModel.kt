package com.android.topscare.ui.scanning

import android.content.Context
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.android.topscare.R
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.domain.model.CountRequest
import com.android.topscare.domain.model.OrderRequest
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.model.ReceiveRequest
import com.android.topscare.domain.usecase.*
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.base.Event
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext

class PrepareScanViewModel @ViewModelInject constructor(
    private val getProductByKeyUseCase: GetProductByKeyUseCase,
    private val insertCountProductUseCase : InsertCountProductUseCase,
    private val insertOrderProductUseCase: InsertOrderProductUseCase,
    private val insertReceiveProductUseCase: InsertReceiveProductUseCase,
    private val appSettingUseCase: AppSettingUseCase,
    @ApplicationContext private val context: Context
) : BaseViewModel<Unit>() {
    val _onBackPressed = SingleLiveEvent<Any>()
    val _onCameraPressed = SingleLiveEvent<Any>()
    val _scanMode = SingleLiveEvent<ScanMode>()
    val _product = SingleLiveEvent<ProductResponse>()
    val _onHistoryPressed = SingleLiveEvent<Any>()
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

    fun onHistoryPressed(){
        _onHistoryPressed()
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

    fun insertOrder(request: OrderRequest) = launchRequest{
        val data = insertOrderProductUseCase(
            OrderRequest(request.id, request.amount,request.free, appSettingUseCase.getHhName())
        )
        _dataStates.postValue(DataState.Success(getViewState()))
        if(!data){
            _dataStates.value = DataState.Error(Unit, Event(Exception("")))
        }else{
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertReceive(request: ReceiveRequest) = launchRequest{
        val data = insertReceiveProductUseCase(
            ReceiveRequest(request.id, request.amount,request.expDate, request.lot, appSettingUseCase.getHhName())
        )
        _dataStates.postValue(DataState.Success(getViewState()))
        if(!data){
            _dataStates.value = DataState.Error(Unit, Event(Exception("")))
        }else{
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}