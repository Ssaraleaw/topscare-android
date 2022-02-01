package com.android.topscare.ui.reveive

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
import com.android.topscare.databinding.FragmentReceiveDialogBinding
import com.android.topscare.domain.model.OrderRequest
import com.android.topscare.domain.model.ReceiveRequest
import com.android.topscare.lib_base.base.BaseDialogFragment
import com.android.topscare.lib_base.base.Event
import com.android.topscare.lib_base.extension.isDateFormat
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.lib_base.state.DataState
import com.android.topscare.ui.count.CountDialogFragmentArgs
import com.fastaccess.datetimepicker.DatePickerFragmentDialog
import com.fastaccess.datetimepicker.callback.DatePickerCallback
import java.text.SimpleDateFormat
import java.util.*

class ReceiveDialogFragment : BaseDialogFragment(), DatePickerCallback {
    lateinit var binding: FragmentReceiveDialogBinding
    private val viewModel: ReceiveDialogViewModel by viewModels()
    private val args by navArgs<CountDialogFragmentArgs>()
    private val navHostViewModel: NavHostViewModel by activityViewModels()

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
            observe(_onSavePressed){
                viewModel._amountUiState.postValue(validateAmount())
                viewModel._lotUiState.postValue(validateLotNo())
                viewModel._expUiState.postValue(validateExpDate())
                if (validateAmount().isError() != true &&
                    validateLotNo().isError() != true &&
                    validateExpDate().isError() != true){
                    viewModel._id.value?.let {
                        navHostViewModel._doReceive.value = ReceiveRequest(
                            it,
                            (viewModel._amount.value?:"0").toInt(),
                            viewModel._exp.value?:"",
                            viewModel._lot.value?:"0",
                            "" )
                    }
                    navController.popBackStack()
                }
            }
            observe(_onDatePickPressed){
                DatePickerFragmentDialog.newInstance().show(childFragmentManager, "DatePickerFragmentDialog")
            }
        }
    }

    private fun validateAmount() : DataState<Unit> {
        return when {
            viewModel._amount.value.isNullOrBlank() || viewModel._amount.value!!.toInt() <= 0 -> {
                DataState.Error(Unit, Event(Exception(getString(R.string.label_error_data_empty))))
            }
            else -> {
                DataState.Success(Unit)
            }
        }
    }
    private fun validateLotNo() : DataState<Unit> {
        return when {
            viewModel._lot.value.isNullOrBlank() -> {
                DataState.Error(Unit, Event(Exception(getString(R.string.label_error_data_empty))))
            }
            else -> {
                DataState.Success(Unit)
            }
        }
    }
    private fun validateExpDate() : DataState<Unit> {
        return when {
            viewModel._exp.value.isNullOrBlank() || !viewModel._exp.value!!.isDateFormat("yyyy-MM-dd")-> {
                DataState.Error(Unit, Event(Exception(getString(R.string.label_error_data_empty))))
            }
            else -> {
                DataState.Success(Unit)
            }
        }
    }

    override fun onDateSet(date: Long) {
        val _date = Date(date)
        val format = SimpleDateFormat("yyyy-MM-dd")
        viewModel._exp.value = format.format(_date)
    }
}