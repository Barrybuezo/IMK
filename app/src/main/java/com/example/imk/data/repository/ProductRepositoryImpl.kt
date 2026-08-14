package com.example.imk.data.repository

import com.example.imk.data.local.dao.ProductDao
import com.example.imk.data.local.entity.ProductEntity
import com.example.imk.data.mapper.toDomain
import com.example.imk.data.mapper.toEntity
import com.example.imk.domain.model.Product
import com.example.imk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    override val viewProducts: Flow<List<Product>> //Usar get() = ... aquí es redundancia
            = productDao.viewProducts().map { list ->
        list.map { it ->
            it.toDomain()
        }
    }

    override fun searchProduct(name: String): Flow<List<Product>> =
        productDao.searchProduct(name).map { list ->
            list.map { it ->
                it.toDomain()
            }
        }


    override suspend fun add(
        name: String,
        stock: Int,
        price: Double,
        photoUri: String?
    ) {
//        productDao.add(ProductEntity(id = 0, name, stock, price, photoUri)) //forma directa sin usar Mapper
        val productToDomain = Product(id = 0, name, stock, price, photoUri)
        productDao.add(productToDomain.toEntity())
    }

    override suspend fun edit(
        id: Int,
        name: String,
        stock: Int,
        price: Double,
        photoUri: String?
    ) {
//        productDao.edit(ProductEntity(id, name, stock, price, photoUri)) //forma directa sin usar Mapper
        val productToDomain = Product(id, name, stock, price, photoUri)
        productDao.edit(productToDomain.toEntity())
    }

    override suspend fun delete(id: Int) {
        productDao.delete(id)
    }

    override suspend fun getProductById(id: Int) = productDao.getProductById(id)?.toDomain()
}