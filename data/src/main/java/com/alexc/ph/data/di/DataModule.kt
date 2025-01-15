package com.alexc.ph.data.di

import com.alexc.ph.data.repository.ProductsRepositoryImpl
import com.alexc.ph.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository
}