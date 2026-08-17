package com.example.imk.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imk.domain.model.Product
import com.example.imk.domain.usecase.GetProductsUseCase
import com.example.imk.domain.usecase.SearchProductUseCase
import com.example.imk.ui.product_detail.ProductDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class InventoryViewModel(
    private val viewProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductUseCase
) : ViewModel() {
    //Guarda lo que el usuario escribe / Estado del buscador
    private val queryFlow = MutableStateFlow("")

    //Cada vez que el usuario escribe algo la UI llama esta función para detectar que producto puede estar buscando
    fun onSearchQueryChanged(query: String) {
        queryFlow.value = query
    }

    // Cambio de Flow a StateFlow
    val productsFlow: StateFlow<List<Product>> =
        queryFlow.flatMapLatest { query ->
            if (query.isBlank()) {
                viewProductsUseCase() //Muestra todo si la barra de busqueda está vacia
            } else {
                searchProductsUseCase(query) //Muestra los productos filtrados al buscar
            }
        }.stateIn(
            scope = viewModelScope,
            //Observa durante 5 segundos para sobrevivir a cambios de estados como rotaciones y no tener cargar todo desde 0
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList() // Empieza con una lista vacía mientras carga de la base de datos
        )

}