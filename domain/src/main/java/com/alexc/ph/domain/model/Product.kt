package com.alexc.ph.domain.model

data class Product(
    val id: Long = 0,
    val name: String,
    val categoryId: Long,
    val description: String
)
