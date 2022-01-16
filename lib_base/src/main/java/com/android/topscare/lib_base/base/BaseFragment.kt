package com.android.topscare.lib_base.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {
    val navController: NavController by lazy { findNavController() }

}