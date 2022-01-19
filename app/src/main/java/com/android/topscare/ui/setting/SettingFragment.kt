package com.android.topscare.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.android.topscare.R
import com.android.topscare.databinding.FragmentSettingBinding
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.lib_base.extension.showChromeCustomTab

class SettingFragment : BaseFragment() {
    private val viewModel: SettingViewModel by viewModels()
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
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
            observe(_onAboutPressed){
                navigateToAboutUsPage()
            }
            observe(_onBackPressed){
                navController.popBackStack()
            }
            observe(_onTutorialPressed){
                navigateToTutorialPage()
            }
            observe(_onSoftwareUpdatePressed){
                navigateToUpdatePage()
            }
        }
    }

    private fun navigateToAboutUsPage() {
        navController.navigate(R.id.aboutDialogFragment)
    }
    private fun navigateToTutorialPage() {
        navController.navigate(SettingFragmentDirections.actionSettingFragmentToTutorialFragment())
    }
    private fun navigateToUpdatePage() {
        requireContext().showChromeCustomTab(SOFTWARE_UPDATE.toUri())
    }

    companion object {
        const val SOFTWARE_UPDATE = "https://www.accessory.worldmedic.com/smartstock/update"
    }
}