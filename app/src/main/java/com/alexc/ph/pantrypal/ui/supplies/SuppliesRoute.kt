package com.alexc.ph.pantrypal.ui.supplies

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SuppliesRoute

fun NavController.navigateToSupplies(navOptions: NavOptions) =
    navigate(route = SuppliesRoute, navOptions)

fun NavGraphBuilder.suppliesScreen() {
    composable<SuppliesRoute> {
        SuppliesScreen()
    }
}