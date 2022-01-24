package com.android.topscare.ui.history.receive.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.topscare.NavHostViewModel
import com.android.topscare.R
import com.android.topscare.databinding.FragmentConfirmDeleteReceiveDialogBinding
import com.android.topscare.lib_base.base.BaseDialogFragment
import com.android.topscare.lib_base.extension.observe

class ConfirmDeleteReceiveDialogFragment : BaseDialogFragment() {
    private lateinit var binding: FragmentConfirmDeleteReceiveDialogBinding
    private val viewModel: ConfirmDeleteReceiveDialogViewModel by viewModels()
    private val args by navArgs<ConfirmDeleteReceiveDialogFragmentArgs>()
    private val navHostViewModel: NavHostViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentConfirmDeleteReceiveDialogBinding.inflate(LayoutInflater.from(context), null, false)
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
        viewModel.init( args.title,args.desc)
        registerObserver()
    }

    private fun registerObserver() {
        with(viewModel) {
            observe(_onCancelPressed){
                navController.popBackStack()
            }
            observe(_onConfirmPressed){
                navHostViewModel._onConfirmDeleteReceiveItemEvent.postValue(args.receiveHistoryResponse)
                navController.popBackStack()
            }
        }
    }
}