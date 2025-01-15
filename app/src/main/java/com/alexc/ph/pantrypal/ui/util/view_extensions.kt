package com.alexc.ph.pantrypal.ui.util

import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.model.DefaultCategories
import com.alexc.ph.pantrypal.R

fun Category.getIconFromCategory(): Int {
    val categoryType = DefaultCategories.fromString(this.name)
    return when(categoryType) {
        DefaultCategories.GROCERY -> R.drawable.ic_grocery
        DefaultCategories.MEAT_FISH -> R.drawable.ic_fish
        DefaultCategories.EGGS_DAIRY -> R.drawable.ic_eggs_dairy
        DefaultCategories.DRINKS -> R.drawable.ic_drinks
        DefaultCategories.CEREALS_PASTA -> R.drawable.ic_cereal_pasta
        DefaultCategories.FROZEN_FOOD -> R.drawable.ic_frozen_food
        DefaultCategories.CANNED_FOOD -> R.drawable.ic_canned_food
        DefaultCategories.CONDIMENTS -> R.drawable.ic_condiments
        DefaultCategories.BREAD_BAKERY -> R.drawable.ic_bread_confectionery
        else -> R.drawable.ic_products
    }
}