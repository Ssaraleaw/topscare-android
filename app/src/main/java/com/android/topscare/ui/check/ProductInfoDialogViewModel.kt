package com.android.topscare.ui.check

import androidx.lifecycle.MutableLiveData
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.lib_base.base.BaseViewModel
import com.android.topscare.lib_base.extension.toCurrencyFormatWithoutDecimal
import com.android.topscare.lib_base.state.SingleLiveEvent

class ProductInfoDialogViewModel : BaseViewModel<Unit>() {
    val _onClosePressed = SingleLiveEvent<Any>()
    val _name = MutableLiveData<String>()
    val _cname = MutableLiveData<String>()
    val _price = MutableLiveData<String>()
    val _barcode = MutableLiveData<String>()
    val _store = MutableLiveData<String>()
    val _amount = MutableLiveData<Int>()
    val _code = MutableLiveData<String>()
    val _unit = MutableLiveData<String>()
    val _packing = MutableLiveData<String>()
    fun onClosePressed(){
        _onClosePressed()
    }

    fun init(product : ProductResponse){
        _packing.value = if(product.packing.isNullOrEmpty()) "N/A" else product.packing
        _unit.value = if(product.unit.isNullOrEmpty()) "N/A" else product.unit
        _barcode.value = if(product.barcode.isNullOrEmpty()) "N/A" else product.barcode
        _code.value = if(product.id.isNullOrEmpty()) "N/A" else product.id
        _name.value = if(product.name.isNullOrEmpty()) "N/A" else product.name
        _cname.value = if(product.commonName.isNullOrEmpty()) "N/A" else product.commonName
        product.price?.let {
            _price.value = it.toString().toCurrencyFormatWithoutDecimal()
        }
        _store.value = if(product.stay.isNullOrEmpty()) "N/A" else product.stay
        _amount.value = product.amount
    }
}