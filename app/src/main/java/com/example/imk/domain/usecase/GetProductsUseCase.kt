package com.example.imk.domain.usecase

import com.example.imk.domain.model.Product
import com.example.imk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow


class GetProductsUseCase(private val productRepository: ProductRepository){
    operator fun invoke() : Flow<List<Product>>{
        return productRepository.viewProducts
    }
}