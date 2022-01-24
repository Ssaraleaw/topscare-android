package com.android.topscare.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
        binding.searchView.isActivated = true
        binding.searchView.queryHint = "Search"
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel._search.value = query
                viewModel.getCountHistory()
                hideKeyboard()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    viewModel._search.value = ""
                    viewModel.getCountHistory()
                    return true
                }
                return false
            }
        })
        binding.searchView.onActionViewExpanded()
        binding.searchView.isIconified = false
        binding.searchView.clearFocus()
        registerObserver()
    }
    private fun registerObserver() {
        with(viewModel) {
            observe(_onBackPressed){
                navController.popBackStack()
            }
            observe(productCountList){
                binding.adapter?.submitList(it)
            }
        }
    }
    private fun onCountItemClicked(countResponse: CountResponse) {

    }
}