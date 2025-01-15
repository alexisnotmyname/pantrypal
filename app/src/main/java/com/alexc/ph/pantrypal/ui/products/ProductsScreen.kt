package com.alexc.ph.pantrypal.ui.products

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.domain.model.Category
import com.alexc.ph.pantrypal.R
import com.alexc.ph.pantrypal.ui.components.GenericTopAppBar
import com.alexc.ph.pantrypal.ui.components.PantryPalIcons
import com.alexc.ph.pantrypal.ui.components.PantryPalTopAppBar
import com.alexc.ph.pantrypal.ui.components.SwipeableBox
import com.alexc.ph.pantrypal.ui.theme.PantryPalTheme
import com.alexc.ph.pantrypal.ui.util.getIconFromCategory
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    productsViewModel: ProductsViewModel = hiltViewModel(),
    navigateToProductList: (Category) -> Unit
) {
    var showAddCategory by rememberSaveable { mutableStateOf(false) }
    var showDeleteConfirmDialog by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf<Category?>(null) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    val uiState by productsViewModel.uiState.collectAsStateWithLifecycle()
    if(uiState is ProductsUiState.Success) {
        val categories = (uiState as ProductsUiState.Success).categories
        ProductCategoryScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            categories = categories,
            onAddCategoryClicked = { showAddCategory = true },
            onCategorySelected = { category ->
                println("selected category $category")
                navigateToProductList(category)
            },
            showDeleteConfirmDialog = showDeleteConfirmDialog,
            onCategoryDelete = { category ->
                showDeleteConfirmDialog = true
                selectedCategory = category
            }
        )
    }

    if (showAddCategory) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showAddCategory = false }
        ) {
            AddCategory(
                onBackClicked = { showAddCategory = false },
                onSaveClicked = {
                    productsViewModel.addCategory(it)
                    showAddCategory = false
                }
            )
        }
    }

    if(showDeleteConfirmDialog && selectedCategory != null) {
        ConfirmDeleteCategory(
            category = selectedCategory?.name ?: "",
            onDismiss = { showDeleteConfirmDialog = false },
            onYesClicked = {
                showDeleteConfirmDialog = false
                selectedCategory?.let { productsViewModel.deleteCategory(it) }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCategoryScreen(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onAddCategoryClicked: () -> Unit = {},
    onCategorySelected: (category: Category) -> Unit = {},
    showDeleteConfirmDialog: Boolean = false,
    onCategoryDelete: (category: Category) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
        PantryPalTopAppBar(
            title = stringResource(R.string.products),
            action = {
                IconButton(onClick = onAddCategoryClicked) {
                    Icon(
                        imageVector = PantryPalIcons.Add,
                        contentDescription = "Action icon",
                    )
                }
            }
        )

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = categories, key = { category -> category.id }) { category ->
                if(category.deletable) {
                    val deleteComponentWidthSize = 80.dp
                    SwipeableBox(
                        modifier = Modifier
                            .fillMaxWidth(),
                        content = { modifier ->
                            ProductCategoryItem(
                                modifier = modifier,
                                category = category,
                                categoryIcon = category.getIconFromCategory(),
                                onCategorySelected = onCategorySelected
                            )
                        },
                        secondContent = { modifier ->
                            DeleteButton(
                                modifier = modifier
                                    .width(deleteComponentWidthSize)
                                    .padding(start = 4.dp),
                                onClick = {
                                    onCategoryDelete(category )
                                })
                        },
                        offsetSize = deleteComponentWidthSize,
                        returnInitialState = showDeleteConfirmDialog
                    )
                } else {
                    ProductCategoryItem(
                        modifier = Modifier.fillMaxWidth(),
                        category = category,
                        categoryIcon = category.getIconFromCategory(),
                        onCategorySelected = onCategorySelected
                    )
                }
            }
        }
    }
}

@Composable
fun AddCategory(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onSaveClicked: (category: String) -> Unit = {}
) {
    var newCategory by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(4.dp)
    ) {
        GenericTopAppBar(
            title = stringResource(R.string.new_category),
            navigation = {
                IconButton(
                    onClick = onBackClicked
                ) {
                    Icon(
                        imageVector = PantryPalIcons.Back,
                        contentDescription = "Back",
                    )
                }
            },
            action = {
                IconButton(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = {
                        if(newCategory.isEmpty()) {
                            isError = true
                            return@IconButton
                        }
                        onSaveClicked(newCategory)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        )

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                textStyle = MaterialTheme.typography.labelLarge,
                value = newCategory,
                onValueChange = {
                    newCategory = it
                    isError = it.isEmpty()
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.hint_category_name))
                },
                isError = isError,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        if(isError) {
            Text(
                text = stringResource(R.string.enter_category_name),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun ProductCategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    @DrawableRes categoryIcon: Int = R.drawable.ic_products,
    onCategorySelected: (category: Category) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onCategorySelected(category) }
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(categoryIcon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
            Text(
                text = category.name,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = PantryPalIcons.Forward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun ConfirmDeleteCategory(
    category: String,
    onDismiss: () -> Unit = {},
    onYesClicked: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Confirm Delete")
        },
        text = {
            Text("Are you sure you want to delete $category?")
        },
        confirmButton = {
            TextButton(onClick = onYesClicked) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}


@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect (key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = true,
        exit = shrinkVertically (
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeToDismissBoxState = state)
            },
            content = { content(item) },
            enableDismissFromStartToEnd = false,
            enableDismissFromEndToStart = true
        )
    }
}

@Composable
fun DeleteButton(modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun DeleteBackground(
    swipeToDismissBoxState: SwipeToDismissBoxState
) {
    val color = if(swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else {
        Color.Transparent
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    PantryPalTheme(dynamicColor = false) {
        val category = Category(1, "Category 1", true)
        ProductCategoryItem(
            modifier = Modifier.fillMaxWidth(),
            category = category
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddCategoryPreview() {
    PantryPalTheme(dynamicColor = false) {
        AddCategory(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCategoryScreenPreview() {
    val categories = listOf(
        Category(1, "Category 1", true),
        Category(2, "Category 2", true),
        Category(3, "Category 3", true),
        Category(4, "Category 4", true),
    )
    PantryPalTheme(dynamicColor = false) {
        Surface {
            ProductCategoryScreen(
                modifier = Modifier.fillMaxSize(),
                categories = categories
            )
        }
    }
}

