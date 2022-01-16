package com.android.topscare.lib_base.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.topscare.lib_base.state.DataState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    protected val _dataStates: MutableLiveData<DataState<T>> = MutableLiveData()
    val dataStates: LiveData<DataState<T>> = _dataStates

    protected fun launchRequest(block: suspend () -> Unit): Job {
        val currentViewState = getViewState()
        return viewModelScope.launch {
            try {
                _dataStates.postValue(DataState.Loading(currentViewState))
                block()
            } catch (exception: Exception) {
                _dataStates.postValue(DataState.Error(currentViewState, Event(exception)))
            }
        }
    }

    protected fun getViewState(): T? = _dataStates.value?.toData()
}