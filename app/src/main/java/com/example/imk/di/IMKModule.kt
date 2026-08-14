package com.example.imk.di

import androidx.room.Room
import com.example.imk.data.local.database.IMKDatabase
import com.example.imk.data.repository.ProductRepositoryImpl
import com.example.imk.domain.repository.ProductRepository
import com.example.imk.domain.usecase.AddProductUseCase
import com.example.imk.domain.usecase.DeleteProductUseCase
import com.example.imk.domain.usecase.EditProductUseCase
import com.example.imk.domain.usecase.GetProductsUseCase
import com.example.imk.domain.usecase.SearchProductUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

//Manual de instrucciones
private const val IMK_DATABASE = "imk_database" //Buena practica de programación

val roomModule = module {
    single {
        Room.databaseBuilder( //Crear la base de datos
            context = androidContext(), //Dirección de la app para guardar la base de datos
            klass = IMKDatabase::class.java, //Diseño de cómo debe ser la base de datos para crear el archivo fisico
            name = IMK_DATABASE
        ).fallbackToDestructiveMigration()//En caso de actualización, destruye el archivo fisico viejo y construye uno nuevo con el nuevo diseño(plano)
            .build()
    }

    single { get<IMKDatabase>().productDao() } //Extrae el DAO de la base de datos
}

val appDataModule = module {
    singleOf(::ProductRepositoryImpl) { bind<ProductRepository>() } //Para que los casos de uso pueden trabajar con IMPL
}

//Construir casos de uso
val appDomainModule = module {
    factoryOf(::GetProductsUseCase)
    factoryOf(::AddProductUseCase)
    factoryOf(::EditProductUseCase)
    factoryOf(::DeleteProductUseCase)
    factoryOf(::SearchProductUseCase)
}