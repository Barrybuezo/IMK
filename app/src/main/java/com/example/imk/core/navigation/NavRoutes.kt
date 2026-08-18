package com.example.imk.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class NavRoutes : NavKey { //NavVackStack asegurado
    //Rutas autorizadas para ser agregadas al historial
    @Serializable
    data object Inventory : NavRoutes()
//Si es null es nuevo registro
    @Serializable
    data class Form(val productId: Int? = null) : NavRoutes()

    //Siempre pasar solo el identificador necesario, no más
    @Serializable
    data class Detail(val productId: Int) : NavRoutes()
}