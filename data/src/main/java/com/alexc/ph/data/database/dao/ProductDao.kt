package com.alexc.ph.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexc.ph.data.database.model.CategoryEntity
import com.alexc.ph.data.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products where categoryId = :categoryId")
    fun getProductByCategory(categoryId: Long): Flow<List<ProductEntity>>
}