package com.example.imk.ui.inventory

import androidx.lifecycle.ViewModel
import com.example.imk.data.local.database.IMKDatabase
import com.example.imk.domain.model.Product
import com.example.imk.domain.usecase.GetProductsUseCase
import com.example.imk.domain.usecase.SearchProductUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

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

    val productsFlow: Flow<List<Product>> =
        queryFlow.flatMapLatest { query ->
            if (query.isBlank()) {
                viewProductsUseCase() //Muestra todo si la barra de busqueda está vacia
            } else {
                searchProductsUseCase(query) //Muestra los productos filtrados al buscar
            }
        }
}