package com.example.imk

import android.app.Application
import com.example.imk.di.appDataModule
import com.example.imk.di.appDomainModule
import com.example.imk.di.appUiModule
import com.example.imk.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

//El fin es leer los manuales de instrucciones
class IMKApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger() // Para que Koin nos avise de errores en la consola
            androidContext(this@IMKApp) // Le damos la dirección de la app
            modules(
                roomModule,
                appDataModule,
                appDomainModule,
                appUiModule
            ) //Entregamos nuestros manuales de instrucciones
        }
    }
}