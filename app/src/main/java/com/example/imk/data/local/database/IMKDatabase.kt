package com.example.imk.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imk.data.local.dao.ProductDao
import com.example.imk.data.local.entity.ProductEntity

//Biblioteca
@Database(entities = [ProductEntity::class], version = 1)//Crea este archivo físico en el teléfono, mete estos archiveros adentro
abstract class IMKDatabase : RoomDatabase(){
    abstract fun productDao() : ProductDao //Si alguien quiere buscar algo, pásale a este bibliotecario.
}