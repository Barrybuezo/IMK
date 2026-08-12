package com.example.imk.domain.model

sealed class AddProductResult {
    data object Success : AddProductResult()
    data object EmptyInformation : AddProductResult()
    data class InvalidInformation(val message : String) : AddProductResult()
}