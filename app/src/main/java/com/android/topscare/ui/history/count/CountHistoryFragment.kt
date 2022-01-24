package com.android.topscare.ui.history.count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.topscare.NavHostViewModel
import com.android.topscare.R
import com.android.topscare.databinding.FragmentCountHistoryBinding
import com.android.topscare.domain.model.CountResponse
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.addLineItemDecoration
import com.android.topscare.lib_base.extension.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountHistoryFragment : BaseFragment() {
    private val viewModel: CountHistoryViewModel by viewModels()
    private lateinit var binding: FragmentCountHistoryBinding
    private val navHostViewModel: NavHostViewModel by activityViewModels()

    val adapter: PagedCountHistoryListAdapter by lazy {
        PagedCountHistoryListAdapter(
            this::onCountItemClicked,
            this::onCountItemDeleteClicked
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountHistoryBinding.inflate(inflater, container, false)
        binding.layoutManager = LinearLayoutManager(context)
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
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.onSwipeRefresh() }
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
            observe(_product){
                navigateToCheckDialog(it)
            }
            observe(_deleteSuccess){
                viewModel.getCountHistory()
            }
            observe(_swipeRefresh){
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
        with(navHostViewModel){
            observe(_onConfirmDeleteCountItemEvent){
                viewModel.deleteCountProduct(it.id)
            }
        }
    }
    private fun onCountItemClicked(countResponse: CountResponse) {
        viewModel.getProductByKey(key = countResponse.barcode)
    }
    private fun onCountItemDeleteClicked(countResponse: CountResponse) {
        navController.navigate(
            CountHistoryFragmentDirections.actionCountHistoryFragmentToConfirmDeleteDialogFragment(
                countResponse,
                getString(R.string.title_delete),
                getString(R.string.desc_delete, countResponse.name, countResponse.amount),
            )
        )
    }

    private fun navigateToCheckDialog(product: ProductResponse) {
        try {
            if (navController.currentDestination?.id == R.id.countHistoryFragment) {
                navController.navigate(
                    CountHistoryFragmentDirections.actionCountHistoryFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            } else {
                navController.popBackStack(R.id.countHistoryFragment, false)
                navController.navigate(
                    CountHistoryFragmentDirections.actionCountHistoryFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}