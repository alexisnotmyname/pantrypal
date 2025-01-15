package com.alexc.ph.pantrypal.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.alexc.ph.domain.model.Category
import com.alexc.ph.pantrypal.ui.navigation.AppDestinations
import com.alexc.ph.pantrypal.ui.navigation.AppDestinations.*
import com.alexc.ph.pantrypal.ui.products.list.navigateToProductList
import com.alexc.ph.pantrypal.ui.products.navigateToProducts
import com.alexc.ph.pantrypal.ui.shoppinglist.navigateToShoppingList
import com.alexc.ph.pantrypal.ui.supplies.navigateToSupplies

@Composable
fun rememberPantryPalAppState(
    navController: NavHostController = rememberNavController(),
): PantryPalAppState {
    return remember(
        navController,
    ) {
        PantryPalAppState(
            navController = navController
        )
    }
}

class PantryPalAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: AppDestinations?
        @Composable get() {
            return AppDestinations.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) ?: false
            }
        }

    val topLevelDestinations: List<AppDestinations> = AppDestinations.entries

    fun navigateToTopLevelDestination(appDestinations: AppDestinations) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when(appDestinations) {
            PRODUCTS -> navController.navigateToProducts(topLevelNavOptions)
            SUPPLIES -> navController.navigateToSupplies(topLevelNavOptions)
            SHOPPING_LIST -> navController.navigateToShoppingList(topLevelNavOptions)
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToProductList(category: Category) {
        navController.navigateToProductList(category.id)
    }
}