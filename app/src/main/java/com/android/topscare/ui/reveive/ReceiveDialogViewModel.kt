package com.android.topscare.ui.reveive

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.extension.toCurrencyFormatWithoutDecimal
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent

class ReceiveDialogViewModel @ViewModelInject constructor(

) : BaseViewModel<Unit>(){
    val _id = MutableLiveData<String>()
    val _onClosePressed = SingleLiveEvent<Any>()
    val _onDatePickPressed = SingleLiveEvent<Any>()
    val _name = MutableLiveData<String>()
    val _cname = MutableLiveData<String>()
    val _price = MutableLiveData<String>()
    val _barcode = MutableLiveData<String>()
    val _store = MutableLiveData<String>()
    val _amountUiState = MediatorLiveData<DataState<Unit>>()
    val _amount = MutableLiveData<String>()
    val _unit = MutableLiveData<String>()
    val _lotUiState = MediatorLiveData<DataState<Unit>>()
    val _lot = MutableLiveData<String>()
    val _exp = MutableLiveData<String>()
    val _expUiState = MediatorLiveData<DataState<Unit>>()
    val _onSavePressed = SingleLiveEvent<Any>()

    fun onClosePressed(){
        _onClosePressed()
    }
    fun onSavePressed(){
        _onSavePressed()
    }
    fun onDatePickPressed(){
        _onDatePickPressed()
    }
    fun init(product : ProductResponse){
        _expUiState.value = DataState.Success(Unit)
        product.barcode?.let {
            _barcode.value = it
        }
        product.id?.let {
            _id.value = it
        }
        _unit.value = if(product.unit.isNullOrEmpty()) "N/A" else product.unit
        _name.value = if(product.name.isNullOrEmpty()) "N/A" else product.name
        _cname.value = if(product.commonName.isNullOrEmpty()) "N/A" else product.commonName
        product.price?.let {
            _price.value = it.toString().toCurrencyFormatWithoutDecimal()
        }
        _store.value = if(product.stay.isNullOrEmpty()) "N/A" else product.stay
    }
}