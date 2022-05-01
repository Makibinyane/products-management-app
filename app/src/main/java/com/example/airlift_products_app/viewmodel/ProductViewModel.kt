package com.example.airlift_products_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airlift_products_app.model.Product
import com.example.airlift_products_app.model.ProductResponse
import com.example.airlift_products_app.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository): ViewModel()  {
    val productListLiveData = MutableLiveData<List<ProductResponse>>()
    var addProductLiveData = MutableLiveData<Product>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun fetchProducts() {
        viewModelScope.launch {
            loading.value = true
            val response = repository.getProductList()
            if (response.isSuccessful && response.code() == 200) {
                productListLiveData.value = response.body()
            } else {
                onError()
            }
            loading.value = false
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            val response = repository.addNewProduct(product)
            if (response.isSuccessful && response.code() == 200) {
                addProductLiveData.value = response.body()
            } else {
                onError()
            }
        }
    }

    private fun onError() {
        errorMessage.value = "An error has occurred please try again"
    }
}