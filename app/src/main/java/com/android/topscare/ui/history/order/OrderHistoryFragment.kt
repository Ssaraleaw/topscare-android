package com.android.topscare.ui.history.order

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
import com.android.topscare.domain.model.OrderHistoryResponse
import com.android.topscare.domain.model.ProductResponse
import com.android.topscare.lib_base.base.BaseFragment
import com.android.topscare.lib_base.extension.addLineItemDecoration
import com.android.topscare.lib_base.extension.observe
import com.android.topscare.ui.history.count.CountHistoryFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : BaseFragment() {
    private val viewModel: OrderHistoryViewModel by viewModels()
    private lateinit var binding: FragmentOrderHistoryBinding
    private val navHostViewModel: NavHostViewModel by activityViewModels()

    val adapter: PagedOrderHistoryListAdapter by lazy {
        PagedOrderHistoryListAdapter(
            this::onCountItemClicked,
            this::onCountItemDeleteClicked
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
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
                viewModel.getOrderHistory()
                hideKeyboard()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    viewModel._search.value = ""
                    viewModel.getOrderHistory()
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
            observe(productOrderList){
                binding.adapter?.submitList(it)
            }
            observe(_product){
                navigateToCheckDialog(it)
            }
            observe(_deleteSuccess){
                viewModel.getOrderHistory()
            }
            observe(_swipeRefresh){
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
        with(navHostViewModel){
            observe(_onConfirmDeleteOrderItemEvent){
                viewModel.deleteOrderProduct(it.id)
            }
        }
    }
    private fun onCountItemClicked(orderHistoryResponse: OrderHistoryResponse) {
        viewModel.getProductByKey(key = orderHistoryResponse.barcode)
    }
    private fun onCountItemDeleteClicked(orderHistoryResponse: OrderHistoryResponse) {
        navController.navigate(
            OrderHistoryFragmentDirections.actionOrderHistoryFragmentToConfirmDeleteOrderDialogFragment(
                orderHistoryResponse,
                getString(R.string.title_delete),
                getString(R.string.desc_delete, orderHistoryResponse.name, orderHistoryResponse.amount),
            )
        )
    }

    private fun navigateToCheckDialog(product: ProductResponse) {
        try {
            if (navController.currentDestination?.id == R.id.countHistoryFragment) {
                navController.navigate(
                    OrderHistoryFragmentDirections.actionOrderHistoryFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            } else {
                navController.popBackStack(R.id.countHistoryFragment, false)
                navController.navigate(
                    OrderHistoryFragmentDirections.actionOrderHistoryFragmentToProductInfoDialogFragment(
                        product
                    )
                )
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}