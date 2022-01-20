package com.android.topscare.ui.order

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.topscare.R
import com.android.topscare.databinding.FragmentOrderDialogBinding
import com.android.topscare.lib_base.base.BaseDialogFragment
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.ui.count.CountDialogFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDialogFragment : BaseDialogFragment() {
    lateinit var binding: FragmentOrderDialogBinding
    private val viewModel: OrderDialogViewModel by viewModels()
    private val args by navArgs<CountDialogFragmentArgs>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentOrderDialogBinding.inflate(LayoutInflater.from(context), null, false)
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