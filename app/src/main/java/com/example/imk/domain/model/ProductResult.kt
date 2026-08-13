package com.example.imk.domain.model

sealed class ProductResult {
    data object Success : ProductResult()
    data object EmptyInformation : ProductResult()
    data class InvalidInformation(val message : String) : ProductResult()
}