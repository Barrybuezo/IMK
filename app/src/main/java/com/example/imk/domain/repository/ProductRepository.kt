package com.example.imk.domain.repository

import com.example.imk.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val viewProducts : Flow<List<Product>>
    val searchProduct : Flow<List<Product>>
    suspend fun save(name: String, stock: Int, price: Double, photoUri : String?)
    suspend fun edit(id : Int, name : String, stock : Int, price : Double, photoUri: String?)
    suspend fun delete(id : Int)
}