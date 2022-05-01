package com.example.airlift_products_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.airlift_products_app.R
import com.example.airlift_products_app.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {
    private val args: ProductDetailsFragmentArgs by navArgs()
    private lateinit var productDetailsBinding: FragmentProductDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProductDetailsBinding.bind(view)
        productDetailsBinding = binding
        setProductData()
    }

    private fun setProductData() {
        productDetailsBinding.txtProductDetailsTitle.text = args.product.title
        productDetailsBinding.txtProductDetailsCategory.text = args.product.category
        productDetailsBinding.txtProductDetailsPrice.text = args.product.price.toString()
        Glide.with(this)
            .load(args.product.image)
            .into(productDetailsBinding.txtProductDetailsImage)
        productDetailsBinding.txtProductDetailsDescription.text = args.product.description
    }
}