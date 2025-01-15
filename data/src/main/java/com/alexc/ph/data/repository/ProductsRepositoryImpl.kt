package com.alexc.ph.data.repository

import com.alexc.ph.data.database.dao.CategoryDao
import com.alexc.ph.data.database.dao.ProductDao
import com.alexc.ph.data.database.model.CategoryEntity
import com.alexc.ph.data.database.model.toCategory
import com.alexc.ph.data.database.model.toCategoryEntity
import com.alexc.ph.data.database.model.toProduct
import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.model.Product
import com.alexc.ph.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao
): ProductsRepository {
    override suspend fun getCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map {
            it.map { categoryEntity -> categoryEntity.toCategory()}
        }
    }

    override suspend fun getCategoryById(id: Long): Flow<Category> {
        return categoryDao.getCategoryById(id).map {
            it.toCategory()
        }
    }

    override suspend fun addCategory(category: String) {
        categoryDao.addCategory(CategoryEntity(name =  category))
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toCategoryEntity())
    }

    override suspend fun getProductsByCategory(categoryId: Long): Flow<List<Product>> {
        return productDao.getProductByCategory(categoryId).map {
            it.map { productEntity -> productEntity.toProduct() }
        }
    }
}