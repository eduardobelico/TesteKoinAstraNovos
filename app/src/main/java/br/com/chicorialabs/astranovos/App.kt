package br.com.chicorialabs.astranovos

import android.app.Application
import br.com.chicorialabs.astranovos.data.di.DataModule
import br.com.chicorialabs.astranovos.domain.di.DomainModule
import br.com.chicorialabs.astranovos.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Essa classe representa o ponto de entrada no aplicativo e será a primeira classe executada.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        /**
         * Carregar os módulos
         */

        PresentationModule.load()
        DataModule.load()
        DomainModule.load()
    }

}