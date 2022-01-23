package com.android.topscare.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.topscare.databinding.FragmentCountHistoryBinding
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.addLineItemDecoration
import com.android.topscare.lib_base.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountHistoryFragment : BaseFragment() {
    private val viewModel: CountHistoryViewModel by viewModels()
    private lateinit var binding: FragmentCountHistoryBinding

    val adapter: PagedCountHistoryListAdapter by lazy {
        PagedCountHistoryListAdapter(
            this::onCountItemClicked
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountHistoryBinding.inflate(inflater, container, false)
        binding.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.addLineItemDecoration()
        registerObserver()
    }
    private fun registerObserver() {
        with(viewModel) {
            observe(_onBackPressed){
                navController.popBackStack()
            }
            observe(_onSearchPressed){

            }
            observe(productCountList){
                binding.adapter?.submitList(it)
            }
        }
    }
    private fun onCountItemClicked(countResponse: CountResponse) {

    }
}