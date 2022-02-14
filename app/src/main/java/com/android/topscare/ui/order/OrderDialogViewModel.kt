package com.android.topscare.ui.order

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.extension.toCurrencyFormatWithoutDecimal
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.lib_base.state.SingleLiveEvent

class OrderDialogViewModel @ViewModelInject constructor(

) : BaseViewModel<Unit>(){
    val _onClosePressed = SingleLiveEvent<Any>()
    val _onSavePressed = SingleLiveEvent<Any>()

    val _id = MutableLiveData<String>()
    val _name = MutableLiveData<String>()
    val _cname = MutableLiveData<String>()
    val _price = MutableLiveData<String>()
    val _barcode = MutableLiveData<String>()
    val _store = MutableLiveData<String>()
    val _amountUiState = MediatorLiveData<DataState<Unit>>()
    val _amount = MutableLiveData<String>()
    val _unit = MutableLiveData<String>()
    val _free_amount = MutableLiveData<String>()
    val _freeAmountUiState = MediatorLiveData<DataState<Unit>>()

    fun onClosePressed(){
        _onClosePressed()
    }
    fun onSavePressed(){
        _onSavePressed()
    }

    fun init(product : ProductResponse){
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