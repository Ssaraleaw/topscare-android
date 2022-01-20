package com.android.topscare.ui.reveive

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.topscare.R
import com.android.topscare.databinding.FragmentReceiveDialogBinding
import com.android.topscare.lib_base.base.BaseDialogFragment
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.ui.count.CountDialogFragmentArgs

class ReceiveDialogFragment : BaseDialogFragment() {
    lateinit var binding: FragmentReceiveDialogBinding
    private val viewModel: ReceiveDialogViewModel by viewModels()
    private val args by navArgs<CountDialogFragmentArgs>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentReceiveDialogBinding.inflate(LayoutInflater.from(context), null, false)
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
        args.product?.let{
            viewModel.init(product = it)
        }
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