package com.android.topscare.lib_base.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.android.topscare.lib_base.state.DataState

class PagedListResult<T>(
    val result: LiveData<PagedList<T>> = MutableLiveData(),
    val initialPageUiState: MutableLiveData<DataState<Unit>> = MutableLiveData()
)