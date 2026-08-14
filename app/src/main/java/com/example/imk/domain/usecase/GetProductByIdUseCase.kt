package com.example.imk.domain.usecase

import com.example.imk.domain.model.Product
import com.example.imk.domain.repository.ProductRepository

class GetProductByIdUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(id: Int) = productRepository.getProductById(id)
}