package com.android.topscare.lib_base.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {
    val navController: NavController by lazy { findNavController() }
    fun hideKeyboard() {
        try {
            val view = requireActivity().currentFocus
            if (view != null) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (ex: Exception) {
        }
    }
}