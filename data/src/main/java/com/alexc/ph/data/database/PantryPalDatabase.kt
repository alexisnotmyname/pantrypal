package com.alexc.ph.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexc.ph.data.database.dao.CategoryDao
import com.alexc.ph.data.database.dao.ProductDao
import com.alexc.ph.data.database.model.CategoryEntity
import com.alexc.ph.data.database.model.ProductEntity

@Database(entities = [ProductEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class PantryPalDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "pantrypal_db"
    }

    abstract fun productDao(): ProductDao

    abstract fun categoryDao(): CategoryDao
}