package com.alexc.ph.data.database

import androidx.room.TypeConverter
import com.alexc.ph.domain.model.DefaultCategories

class TypeConverter {

    @TypeConverter
    fun fromDefaultCategories(category: DefaultCategories): String {
        return category.value
    }

    @TypeConverter
    fun toDefaultCategories(value: String): DefaultCategories? {
        return DefaultCategories.fromString(value)
    }
}