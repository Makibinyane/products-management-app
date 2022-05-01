package com.example.airlift_products_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.airlift_products_app.model.Product
import com.example.airlift_products_app.model.ProductResponse
import com.example.airlift_products_app.model.Rating
import com.example.airlift_products_app.repo.ProductRepository
import com.example.airlift_products_app.util.LiveDataTestUtil.getOrAwaitValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response
import retrofit2.Response.success

class ProductViewModelTest {
    private lateinit var testSubject: ProductViewModel

    @Mock
    private lateinit var repository: ProductRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        testSubject = ProductViewModel(repository)
    }

    @Test
    fun `verify if viewmodel returns list with data when service returns results`() {
        runBlocking {
            whenever(repository.getProductList()).thenReturn(productListMock())
            testSubject.fetchProducts()
            val result = testSubject.productListLiveData.getOrAwaitValue().find {
                it.price.equals(22.90) && it.description == "this is my product"
            }
            val res = productListMock().body()?.first()
            assertEquals(res, result)
        }
    }

    @Test
    fun `verify if viewmodel returns an empty product list when service returns empty results`() {
        runBlocking {
            whenever(repository.getProductList()).thenReturn(success(listOf()))
            testSubject.fetchProducts()
            val result = testSubject.productListLiveData.getOrAwaitValue()
            assertEquals(listOf<ProductResponse>(), result)
        }
    }

    @Test
    fun `verify when viewmodel pass correct product details the service returns success`() {
        runBlocking {
            whenever(repository.addNewProduct(getProductMock())).thenReturn(getProductResponse())
            testSubject.addProduct(getProductMock())
            val result = testSubject.addProductLiveData.getOrAwaitValue()
            assertEquals(getProductMock(), result)
        }
    }

    @Test
    fun `verify if viewmodel gets an error when service fetch products returns error response`() {
        runBlocking {
            whenever(repository.getProductList()).thenReturn(getProductsErrorResponse())
            testSubject.fetchProducts()
            val result = testSubject.errorMessage.getOrAwaitValue()
            assertEquals("An error has occurred please try again", result)
        }
    }

    @Test
    fun `verify if viewmodel gets an error when service add product returns error response`() {
        runBlocking {
            whenever(repository.addNewProduct(getProductMock())).thenReturn(addProductErrorResponse())
            testSubject.addProduct(getProductMock())
            val result = testSubject.errorMessage.getOrAwaitValue()
            assertEquals("An error has occurred please try again", result)
        }
    }

    private fun getProductMock() : Product {
        return Product("01", "Prod1", 22.90, "this is my product",
            "product", "myimage")
    }

    private fun getProductResponse(): Response<Product> {
        return success(getProductMock())
    }

    private fun productListMock(): Response<List<ProductResponse>> {
        val rating = Rating(20.5, 300)
        val productResponse1 = ProductResponse("01", "Prod1", 22.90, "this is my product",
            "product", "myimage", rating)

        val productResponse2 = ProductResponse("02", "Prod2", 8.90, "this is my product 2",
            "product 2", "myimage 2", rating)

        val data = listOf(productResponse1, productResponse2)
        return success(data)
    }

    private fun getProductsErrorResponse() : Response<List<ProductResponse>> {
        val rating = Rating(20.5, 300)
        val productResponse1 = ProductResponse("01", "Prod1", 22.90, "this is my product",
            "product", "myimage", rating)
        val data = listOf(productResponse1)
        return success(203, data)
    }

    private fun addProductErrorResponse() : Response<Product> = success(203, getProductMock())
}