package com.example.imk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.imk.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

//Bibliotecario que busca cosas en el archivero
@Dao
interface ProductDao {
    @Query("SELECT * FROM Products")
    fun viewProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Products WHERE name LIKE '%' || :name || '%'")
    fun searchProduct(name: String) : Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(products : ProductEntity)

    @Update
    suspend fun edit(products : ProductEntity)

    @Query("DELETE FROM Products WHERE id = :id")
    suspend fun delete(id : Int)

    @Query("SELECT * FROM Products WHERE id = :id")
    suspend fun getProductById(id : Int) : ProductEntity?
}