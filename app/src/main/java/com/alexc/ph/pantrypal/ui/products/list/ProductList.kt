package com.alexc.ph.pantrypal.ui.products.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.pantrypal.ui.components.PantryPalIcons
import com.alexc.ph.pantrypal.ui.components.PantryPalTopAppBar

@Composable
fun ProductList(
    productListViewModel: ProductListViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {

    val uiState by productListViewModel.productListUiState.collectAsStateWithLifecycle()
    if(uiState is ProductListUiState.Success)
    {
        val category = (uiState as ProductListUiState.Success).productWithCategory?.category
        ProductList(navigateBack = navigateBack, title = category?.name ?: "")
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    title: String,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        PantryPalTopAppBar(
            title = title,
            navigation = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = PantryPalIcons.Back,
                        contentDescription = "Navigation icon",
                    )
                }
            },
            action = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = PantryPalIcons.Add,
                        contentDescription = "Action icon",
                    )
                }
            }
        )

        LazyColumn (
            modifier = Modifier,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){

        }
    }
}

@Preview
@Composable
fun ProductListPreview() {
    ProductList(navigateBack = {}, title = "Products")
}