package com.alexc.ph.pantrypal.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alexc.ph.pantrypal.R
import com.alexc.ph.pantrypal.ui.products.ProductsRoute
import com.alexc.ph.pantrypal.ui.shoppinglist.ShoppingListRoute
import com.alexc.ph.pantrypal.ui.supplies.SuppliesRoute
import kotlin.reflect.KClass

enum class AppDestinations(
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>
) {
    PRODUCTS(
        unselectedIcon = R.drawable.ic_products,
        selectedIcon = R.drawable.ic_products_white,
        iconTextId = R.string.products,
        titleTextId = R.string.products,
        route = ProductsRoute::class
    ),
    SUPPLIES(
        unselectedIcon = R.drawable.ic_supplies,
        selectedIcon = R.drawable.ic_supplies_white,
        iconTextId = R.string.supplies,
        titleTextId = R.string.supplies,
        route = SuppliesRoute::class
    ),
    SHOPPING_LIST(
        unselectedIcon = R.drawable.ic_list,
        selectedIcon = R.drawable.ic_list_white,
        iconTextId = R.string.list,
        titleTextId = R.string.list,
        route = ShoppingListRoute::class
    ),
}