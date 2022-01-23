package com.android.topscare.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.topscare.databinding.FragmentOrderHistoryBinding
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : BaseFragment() {
    private val viewModel: OrderHistoryViewModel by viewModels()
    private lateinit var binding: FragmentOrderHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
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
            observe(_onSearchPressed){

            }
        }
    }
}