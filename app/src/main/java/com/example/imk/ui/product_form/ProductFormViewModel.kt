package com.example.imk.ui.product_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imk.domain.model.ProductResult
import com.example.imk.domain.usecase.AddProductUseCase
import com.example.imk.domain.usecase.EditProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProductFormUiState {
    data object Idle : ProductFormUiState()
    data object Loading : ProductFormUiState()
    data object Success : ProductFormUiState()
    data class Error(val message: String) : ProductFormUiState()
}

class ProductFormViewModel(
    private val addProductUseCase: AddProductUseCase,
    private val editProductUseCase: EditProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductFormUiState>(ProductFormUiState.Idle)//Instrucciones de estado que solo ViewModelPuede usar
    val uiState: StateFlow<ProductFormUiState> = _uiState.asStateFlow()//Para que la Ui pueda conocer el estado pero no cambiarlo

    fun addProduct(name: String, stock: Int, price: Double, photoUri: String?) {
        viewModelScope.launch {
            _uiState.value = ProductFormUiState.Loading
            _uiState.value = when (val result = addProductUseCase(name, stock, price, photoUri)) {
                is ProductResult.Success -> ProductFormUiState.Success
                is ProductResult.EmptyInformation -> ProductFormUiState.Error("Campo obligatorio")
                is ProductResult.InvalidInformation -> ProductFormUiState.Error(result.message)
            }
        }
    }

    fun editProduct(id: Int, name: String, stock: Int, price: Double, photoUri: String?) {
        viewModelScope.launch {
            _uiState.value = ProductFormUiState.Loading
            _uiState.value = when (val result = editProductUseCase(id, name, stock, price, photoUri)) {
                is ProductResult.Success -> ProductFormUiState.Success
                is ProductResult.EmptyInformation -> ProductFormUiState.Error("Campo obligatorio")
                is ProductResult.InvalidInformation -> ProductFormUiState.Error(result.message)
            }
        }
    }
}




