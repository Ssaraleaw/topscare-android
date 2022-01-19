package com.android.topscare.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.android.topscare.NavHostViewModel
import com.android.topscare.R
import com.android.topscare.databinding.FragmentMainMenuBinding
import com.android.topscare.domain.data.ScanMode
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuFragment : BaseFragment() {
    private val viewModel: MainMenuViewModel by viewModels()
    private val navHostViewModel: NavHostViewModel by activityViewModels()
    private lateinit var binding: FragmentMainMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
    }
    private fun registerObserver() {
        with(viewModel) {
            observe(_onCountPressed){
                navigateToCountPage()
//                navigateToErrorPage()
            }
            observe(_onCheckPressed){
                navigateToCheckPage()
            }
            observe(_onOrderPressed){
                navigateToOrderPage()
            }
            observe(_onReceivePressed){
                navigateToReceivePage()
            }
            observe(_onSettingPressed){
                navigateToSettingPage()
            }
        }
    }
    private fun navigateToSettingPage(){
        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToSettingFragment())
    }
    private fun navigateToCheckPage(){
        navHostViewModel.scanMode.value = ScanMode.CHECK
        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToPrepareScanFragment())
    }
    private fun navigateToCountPage(){
        navHostViewModel.scanMode.value = ScanMode.COUNT
        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToPrepareScanFragment())
    }
    private fun navigateToOrderPage(){
        navHostViewModel.scanMode.value = ScanMode.ORDER
        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToPrepareScanFragment())
    }
    private fun navigateToReceivePage(){
        navHostViewModel.scanMode.value = ScanMode.RECEIVE
        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToPrepareScanFragment())
    }
    private fun navigateToErrorPage(){
        navController.navigate(R.id.errorDialogFragment)
    }
}