package com.example.airlift_products_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.airlift_products_app.R
import com.example.airlift_products_app.databinding.FragmentProductListBinding
import com.example.airlift_products_app.model.ProductResponse
import com.example.airlift_products_app.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productListBinding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProductListBinding.bind(view)
        productListBinding = binding

        viewModel.fetchProducts()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.productListLiveData.observe(viewLifecycleOwner) { products ->
            val productListAdapter = ProductListAdapter { item -> handleClick(item) }
            productListBinding.productsRecyclerView.apply {
                itemAnimator = DefaultItemAnimator()
                adapter  = productListAdapter
            }
            productListAdapter.submitList(products)
        }

        productListBinding.btnAddProduct.setOnClickListener {
            findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToAddProductFragment())
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                productListBinding.progressBar.visibility = View.VISIBLE
            } else {
                productListBinding.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(productListBinding.productsRecyclerView, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun handleClick(data: ProductResponse) {
        findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(data))
    }
}