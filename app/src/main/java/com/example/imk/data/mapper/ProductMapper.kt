package com.example.imk.data.mapper

import com.example.imk.data.local.entity.ProductEntity
import com.example.imk.domain.model.Product

//Traductor del modelo ProductoEntity de data hacia el modelo Product de domain
fun ProductEntity.toDomain(): Product{
    return Product(id, name, stock, price, photoUri)
}

//Traductor del modelo Product de domain hacia el modelo ProductoEntity de data
fun Product.toEntity() : ProductEntity{
    return ProductEntity(id, name, stock, price, photoUri)
}