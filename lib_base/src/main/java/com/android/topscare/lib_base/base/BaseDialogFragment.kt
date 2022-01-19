package com.android.topscare.lib_base.base

import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

abstract class BaseDialogFragment: DialogFragment() {
    val navController: NavController by lazy { findNavController() }

}