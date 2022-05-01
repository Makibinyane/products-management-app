package com.example.airlift_products_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.airlift_products_app.R
import com.example.airlift_products_app.databinding.FragmentAddProductBinding
import com.example.airlift_products_app.model.Product
import com.example.airlift_products_app.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddProductFragment : Fragment() {
    private lateinit var addProductBinding: FragmentAddProductBinding
    private val viewModel: ProductViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddProductBinding.bind(view)
        addProductBinding = binding
        handleButtonClick()

    }

    private fun handleButtonClick() {
        addProductBinding.btnSave.setOnClickListener {
            if (isValid()) {
                val product = Product()
                product.category = addProductBinding.txtProductCategory.text.toString()
                product.title = addProductBinding.txtProductTitle.text.toString()
                product.description = addProductBinding.txtProductDescription.text.toString()
                product.price = addProductBinding.txtProductPrice.text.toString().toDouble()

                viewModel.addProduct(product)
                viewModel.addProductLiveData.observe(viewLifecycleOwner) {
                    if (it.id.isNotEmpty()) {
                        Snackbar.make(
                            addProductBinding.txtProductDescription,
                            getString(R.string.add_product_successfully_message),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                viewModel.errorMessage.observe(viewLifecycleOwner) {
                    Snackbar.make(addProductBinding.txtProductDescription, it, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun isValid(): Boolean {
        var isDataValid = true
        if (addProductBinding.txtProductTitle.text?.isEmpty() == true) {
            isDataValid = false
            addProductBinding.txtProductTitle.error = getString(R.string.product_title_error_message)
        }

        if (addProductBinding.txtProductCategory.text?.isEmpty() == true) {
            isDataValid = false
            addProductBinding.txtProductCategory.error = getString(R.string.product_category_error_message)
        }

        if (addProductBinding.txtProductDescription.text?.isEmpty() == true) {
            isDataValid = false
            addProductBinding.txtProductDescription.error = getString(R.string.product_description_error_message)
        }

        if (addProductBinding.txtProductPrice.text?.isEmpty() == true) {
            isDataValid = false
            addProductBinding.txtProductPrice.error = getString(R.string.product_price_error_message)
        }

        if (addProductBinding.txtProductImage.text?.isEmpty() == true) {
            isDataValid = false
            addProductBinding.txtProductImage.error = getString(R.string.product_image_error_message)
        }

        return isDataValid
    }
}