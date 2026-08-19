package com.example.imk.core.util

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

//Herramienta generica para navegar con Navigation3
fun NavBackStack<NavKey>.navigateTo(route: NavKey){
    add(route)
}

fun NavBackStack<NavKey>.backTo(route: NavKey){
    if(isEmpty()) return
    if (route !in this) return

    while (isNotEmpty() && last() != route){
        removeLastOrNull()
    }
}

fun NavBackStack<NavKey>.back(){
    if(isEmpty()) return
    removeLastOrNull()
}