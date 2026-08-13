package com.example.imk.domain.model

data class Product(
    val id: Int,
    val name: String,
    val stock: Int,
    val price: Double,
    val photoUri: String? = null //Dirección de la imagen para tomar o cargar una foto del producto
)
