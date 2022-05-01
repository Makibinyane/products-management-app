package com.example.airlift_products_app.repo

import com.example.airlift_products_app.model.Product
import com.example.airlift_products_app.model.ProductResponse
import com.example.airlift_products_app.service.ProductService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository@Inject constructor(private val service: ProductService) {

    suspend fun getProductList(): Response<List<ProductResponse>> = service.getProductList()
    suspend fun addNewProduct(product: Product): Response<Product> = service.addNewProduct(product)
}