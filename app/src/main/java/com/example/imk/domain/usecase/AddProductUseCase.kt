package com.example.imk.domain.usecase

import com.example.imk.domain.model.AddProductResult
import com.example.imk.domain.repository.ProductRepository

class AddProductUseCase(private val productRepository: ProductRepository) {

    suspend operator fun invoke(
        name: String,
        stock: Int,
        price: Double,
        photoUri: String?
    ): AddProductResult {
        //Validación para nombre obligatorio
        if (name.isBlank()) {
            return AddProductResult.EmptyInformation
        }
        //Validación para stock obligatorio y que no sea negativo
        if (stock < 0) {
            return AddProductResult.InvalidInformation("El stock no debe ser negativo")
        }
        //Validación para precio obligatorio y mayor a 0
        if (price <= 0.0) {
            return AddProductResult.InvalidInformation("El precio debe ser mayor a 0")
        }

        //Si cumple con todas las validaciones entonces se guarda el producto
        productRepository.add(name, stock, price, photoUri)
        return AddProductResult.Success
    }
}