package com.android.topscare.ui.getting_started

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.topscare.databinding.FragmentGettingStartedBinding
import com.android.topscare.lib_base.base.BaseFragment

class GettingStartedFragment : BaseFragment() {
    private val viewModel: GettingStartedViewModel by viewModels()
    private lateinit var binding: FragmentGettingStartedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGettingStartedBinding.inflate(inflater, container, false)
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

        }
    }
}