package com.alexc.ph.pantrypal.ui.shoppinglist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alexc.ph.pantrypal.ui.supplies.SuppliesRoute
import kotlinx.serialization.Serializable

@Serializable
data object ShoppingListRoute

fun NavController.navigateToShoppingList(navOptions: NavOptions) =
    navigate(route = ShoppingListRoute, navOptions)

fun NavGraphBuilder.shoppingListScreen() {
    composable<ShoppingListRoute> {
        ShoppingListScreen()
    }
}