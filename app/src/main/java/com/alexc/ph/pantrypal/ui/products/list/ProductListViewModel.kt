package com.alexc.ph.pantrypal.ui.products.list


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alexc.ph.domain.GetProducts
import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.model.ProductWithCategory
import com.alexc.ph.domain.repository.ProductsRepository
import com.alexc.ph.pantrypal.ui.products.list.ProductListUiState.Error
import com.alexc.ph.pantrypal.ui.products.list.ProductListUiState.Loading
import com.alexc.ph.pantrypal.ui.products.list.ProductListUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProducts: GetProducts,
    private val productsRepository: ProductsRepository
): ViewModel() {

    private val id = savedStateHandle.toRoute<ProductListRoute>().categoryId

    private var _products = MutableStateFlow<ProductWithCategory?>(null)

    val productListUiState: StateFlow<ProductListUiState> = _products
        .onStart { fetchProductsByCategory(id) }
        .map<ProductWithCategory?, ProductListUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

    private fun fetchProductsByCategory(categoryId: Long) {
        viewModelScope.launch {
//            productsRepository.getCategoryById(categoryId).collect {
//                _category.value = it
//            }
            getProducts(categoryId).collect {
                _products.value = it
            }
        }
    }
}

sealed interface ProductListUiState {
    object Loading : ProductListUiState
    data class Error(val throwable: Throwable) : ProductListUiState
    data class Success(val productWithCategory: ProductWithCategory?) : ProductListUiState
}