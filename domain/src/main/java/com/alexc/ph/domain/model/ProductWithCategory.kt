package com.alexc.ph.domain.model

data class ProductWithCategory(
    val category: Category,
    val products: List<Product>
)