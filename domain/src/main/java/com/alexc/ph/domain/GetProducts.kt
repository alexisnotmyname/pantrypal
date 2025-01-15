package com.alexc.ph.domain

import com.alexc.ph.domain.model.ProductWithCategory
import com.alexc.ph.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(categoryId: Long): Flow<ProductWithCategory> {
        return combine(
            productsRepository.getCategoryById(categoryId),
            productsRepository.getProductsByCategory(categoryId)
        ) { category, products ->
            return@combine ProductWithCategory(
                category = category,
                products = products
            )
        }
    }
}