package com.android.topscare.ui.error

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.android.topscare.R
import com.android.topscare.databinding.FragmentErrorDialogBinding
import com.android.topscare.lib_base.base.BaseDialogFragment
import com.android.topscare.lib_base.extension.observe

class ErrorDialogFragment : BaseDialogFragment() {
    lateinit var binding: FragmentErrorDialogBinding
    private val viewModel: ErrorDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentErrorDialogBinding.inflate(LayoutInflater.from(context), null, false)
        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialog)
        builder.setView(binding.root)
        return builder.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
    }

    private fun registerObserver() {
        with(viewModel){
            observe(_onClosePressed){
                navController.popBackStack()
            }
        }
    }
}