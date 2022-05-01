package com.example.airlift_products_app.service

import com.example.airlift_products_app.model.Product
import com.example.airlift_products_app.model.ProductResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductService {
    @GET("products")
    suspend fun getProductList(): Response<List<ProductResponse>>

    @POST("products")
    suspend fun addNewProduct(@Body product: Product): Response<Product>
}