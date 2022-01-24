package com.android.topscare.ui.history.receive

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
import com.android.topscare.databinding.FragmentOrderHistoryBinding
import com.android.topscare.databinding.FragmentReceiveHistoryBinding
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.domain.model.ReceiveHistoryResponse
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.addLineItemDecoration
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.ui.history.count.CountHistoryFragmentDirections
import com.android.topscare.ui.history.order.OrderHistoryFragmentDirections
import com.android.topscare.ui.history.order.OrderHistoryViewModel
import com.android.topscare.ui.history.order.PagedOrderHistoryListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiveHistoryFragment : BaseFragment() {
    private val viewModel: ReceiveHistoryViewModel by viewModels()
    private lateinit var binding: FragmentReceiveHistoryBinding
    private val navHostViewModel: NavHostViewModel by activityViewModels()

    val adapter: PagedReceiveHistoryListAdapter by lazy {
        PagedReceiveHistoryListAdapter(
            this::onCountItemClicked,
            this::onCountItemDeleteClicked
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceiveHistoryBinding.inflate(inflater, container, false)
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
                viewModel.getReceiveHistory()
                hideKeyboard()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    viewModel._search.value = ""
                    viewModel.getReceiveHistory()
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
            observe(productReceiveList){
                binding.adapter?.submitList(it)
            }
            observe(_product){
                navigateToCheckDialog(it)
            }
            observe(_deleteSuccess){
                viewModel.getReceiveHistory()
            }
            observe(_swipeRefresh){
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
        with(navHostViewModel){
            observe(_onConfirmDeleteReceiveItemEvent){
                viewModel.deleteOrderProduct(it.id)
            }
        }
    }
    private fun onCountItemClicked(receiveHistoryResponse: ReceiveHistoryResponse) {
        viewModel.getProductByKey(key = receiveHistoryResponse.barcode)
    }
    private fun onCountItemDeleteClicked(receiveHistoryResponse: ReceiveHistoryResponse) {
        navController.navigate(
            ReceiveHistoryFragmentDirections.actionReceiveHistoryFragmentToConfirmDeleteReceiveDialogFragment(
                receiveHistoryResponse,
                getString(R.string.title_delete),
                getString(R.string.desc_delete, receiveHistoryResponse.name, receiveHistoryResponse.amount),
            )
        )
    }

    private fun navigateToCheckDialog(product: ProductResponse) {
        try {
            if (navController.currentDestination?.id == R.id.receiveHistoryFragment) {
                navController.navigate(
                    ReceiveHistoryFragmentDirections.actionReceiveHistoryFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            } else {
                navController.popBackStack(R.id.receiveHistoryFragment, false)
                navController.navigate(
                    ReceiveHistoryFragmentDirections.actionReceiveHistoryFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}