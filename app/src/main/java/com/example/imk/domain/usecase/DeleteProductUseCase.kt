package com.example.imk.domain.usecase

import com.example.imk.domain.repository.ProductRepository

class DeleteProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(id: Int) = productRepository.delete(id)
}