package com.alexc.ph.pantrypal.ui.products

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alexc.ph.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data object ProductsRoute

fun NavController.navigateToProducts(navOptions: NavOptions) =
    navigate(route = ProductsRoute, navOptions)

fun NavGraphBuilder.productsScreen(
    navigateToProductList: (Category) -> Unit
) {
    composable<ProductsRoute> {
        ProductsScreen(navigateToProductList = navigateToProductList)
    }
}