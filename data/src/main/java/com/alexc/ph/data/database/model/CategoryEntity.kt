package com.alexc.ph.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexc.ph.domain.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val deletable: Boolean = true
)

fun CategoryEntity.toCategory() = Category(
    id = id,
    name = name,
    deletable = deletable
)

fun Category.toCategoryEntity() = CategoryEntity(
    id = id,
    name = name,
    deletable = deletable
)