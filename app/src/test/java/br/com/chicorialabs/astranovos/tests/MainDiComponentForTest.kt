package br.com.chicorialabs.astranovos.tests

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

const val BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

fun configTestAppComponent() = startKoin {
    loadKoinModules(configDataModuleForTest(BASE_URL) + configDomainModuleForTest())
}