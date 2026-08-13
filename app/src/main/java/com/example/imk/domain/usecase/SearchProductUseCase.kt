package com.example.imk.domain.usecase

import com.example.imk.domain.model.Product
import com.example.imk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class SearchProductUseCase(private val productRepository: ProductRepository) {
    //Recibir una variable de entrada tipo texto(query)
    operator fun invoke(query: String): Flow<List<Product>> = productRepository.searchProduct(query)
}