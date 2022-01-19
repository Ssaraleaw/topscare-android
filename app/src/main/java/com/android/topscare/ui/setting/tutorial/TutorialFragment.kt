package com.android.topscare.ui.setting.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.android.topscare.databinding.FragmentTutorialBinding
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.lib_base.extension.showChromeCustomTab

class TutorialFragment : BaseFragment() {
    private val viewModel: TutorialViewModel by viewModels()
    private lateinit var binding: FragmentTutorialBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTutorialBinding.inflate(inflater, container, false)
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
            observe(_onBackPressed){
                navController.popBackStack()
            }
            observe(_onEtcPressed){
                navigateToEtcPage()
            }
            observe(_onPreparePressed){
                navigateToPrepareMasterPage()
            }
            observe(_onScanningProcessPressed){
                navigateToScanningProcessPage()
            }
        }
    }

    private fun navigateToScanningProcessPage(){
        requireContext().showChromeCustomTab(URL_SCANNING.toUri())
    }
    private fun navigateToEtcPage(){
        requireContext().showChromeCustomTab(URL_ETC.toUri())
    }
    private fun navigateToPrepareMasterPage(){
        requireContext().showChromeCustomTab(URL_PREPARE_MASTER.toUri())
    }

    companion object {
        const val URL_SCANNING = "https://www.accessory.worldmedic.com/smartstock"
        const val URL_ETC = "https://www.accessory.worldmedic.com/installation"
        const val URL_PREPARE_MASTER = "https://www.accessory.worldmedic.com/smartstock"
    }
}