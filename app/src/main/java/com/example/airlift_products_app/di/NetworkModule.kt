package com.example.airlift_products_app.di

import com.example.airlift_products_app.service.ProductService
import com.example.airlift_products_app.service.ProductServiceClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideProductService(): ProductService {
        return ProductServiceClient.create()
    }
}