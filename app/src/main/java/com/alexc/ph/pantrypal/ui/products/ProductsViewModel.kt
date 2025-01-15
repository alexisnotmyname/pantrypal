package com.alexc.ph.pantrypal.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.domain.model.Category
import com.alexc.ph.domain.repository.ProductsRepository
import com.alexc.ph.pantrypal.ui.products.ProductsUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
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
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
): ViewModel() {
    private var _category = MutableStateFlow<List<Category>>(emptyList())

    val uiState: StateFlow<ProductsUiState> = _category
        .onStart { getCategories() }
        .map<List<Category>, ProductsUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

    private fun getCategories()  {
        viewModelScope.launch {
            productsRepository.getCategories().collect{
                _category.value = it
            }
        }
    }

    fun addCategory(category: String) {
        viewModelScope.launch {
            productsRepository.addCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            productsRepository.deleteCategory(category)
        }
    }
}

sealed interface ProductsUiState {
    object Loading : ProductsUiState
    data class Error(val throwable: Throwable) : ProductsUiState
    data class Success(val categories: List<Category>) : ProductsUiState
}