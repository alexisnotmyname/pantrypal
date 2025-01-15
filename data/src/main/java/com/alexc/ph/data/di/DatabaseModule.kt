package com.alexc.ph.data.di

import android.content.Context
import androidx.room.Room
import com.alexc.ph.data.database.PantryPalDatabase
import com.alexc.ph.data.database.dao.CategoryDao
import com.alexc.ph.data.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : PantryPalDatabase =
        Room.databaseBuilder(
            context,
            PantryPalDatabase::class.java,
            PantryPalDatabase.DATABASE_NAME
        )
        .createFromAsset("database/pantrypal.db")
        .build()


    @Provides
    @Singleton
    fun provideProductDao(database: PantryPalDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: PantryPalDatabase): CategoryDao {
        return database.categoryDao()
    }
}