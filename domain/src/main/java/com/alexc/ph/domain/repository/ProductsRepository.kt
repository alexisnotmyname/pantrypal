package com.alexc.ph.domain.repository

import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Flow<Category>
    suspend fun addCategory(category: String)
    suspend fun deleteCategory(category: Category)
    suspend fun getProductsByCategory(categoryId: Long): Flow<List<Product>>
}