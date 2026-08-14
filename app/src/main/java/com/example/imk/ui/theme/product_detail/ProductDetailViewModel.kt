package com.example.imk.ui.theme.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imk.domain.model.Product
import com.example.imk.domain.usecase.DeleteProductUseCase
import com.example.imk.domain.usecase.GetProductByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class ProductDetailUiState {
    data object Loading : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
    data object Deleted : ProductDetailUiState()
}

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun getProductById(id: Int) {
        viewModelScope.launch {
            val productObtained = getProductByIdUseCase(id)

            if(productObtained != null){
                _uiState.value = ProductDetailUiState.Success(productObtained)
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch {
            deleteProductUseCase(id)
            _uiState.value = ProductDetailUiState.Deleted
        }
    }
}

