package com.android.topscare.ui.setting.url_endpoint

import android.app.Dialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.android.topscare.R
import com.android.topscare.databinding.FragmentEditUrlEnpointDialogBinding
import com.android.topscare.lib_base.base.BaseDialogFragment
import com.android.topscare.lib_base.base.Event
import com.android.topscare.lib_base.extension.isValidUrl
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.lib_base.state.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUrlEndpointDialogFragment : BaseDialogFragment() {
    lateinit var binding: FragmentEditUrlEnpointDialogBinding
    private val viewModel: EditUrlEndpointDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentEditUrlEnpointDialogBinding.inflate(LayoutInflater.from(context), null, false)
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
            observe(_onSavePressed){
                viewModel._urlUiState.postValue(validateUrl())
                if (validateUrl().isError() != true){
                    viewModel.doSaveUrl()
                    navController.popBackStack()
                }
            }
        }
    }
    private fun validateUrl() : DataState<Unit> {
        return when {
            viewModel._url.value.isNullOrBlank() || viewModel._url.value?.isValidUrl() == false -> {
                DataState.Error(Unit, Event(Exception(getString(R.string.label_error_data_empty))))
            }
            else -> {
                DataState.Success(Unit)
            }
        }
    }
}