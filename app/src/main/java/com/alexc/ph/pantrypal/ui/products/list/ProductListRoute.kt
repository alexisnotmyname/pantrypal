package com.alexc.ph.pantrypal.ui.products.list

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.alexc.ph.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class ProductListRoute(val categoryId: Long)

fun NavController.navigateToProductList(categoryId: Long, navOptions: NavOptions? = null) =
    navigate(route = ProductListRoute(categoryId), navOptions)

fun NavGraphBuilder.productListScreen(
    navigateBack: () -> Unit
) {
    composable<ProductListRoute> (
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ){
        ProductList(navigateBack = navigateBack)
    }
}