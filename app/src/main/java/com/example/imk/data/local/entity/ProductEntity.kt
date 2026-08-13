package com.example.imk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // id = 0 para los nuevos productos que se guarden y que no afecte a los productos ya existentes al editar
    val name: String,
    val stock: Int,
    val price: Double,
    val photoUri: String? = null
)
