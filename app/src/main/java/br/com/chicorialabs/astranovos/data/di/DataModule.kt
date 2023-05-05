package br.com.chicorialabs.astranovos.data.di

import br.com.chicorialabs.astranovos.data.repository.MockAPIService
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.data.repository.PostRepositoryImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Object que organiza as dependências da camada Data
 */

object DataModule {

    fun load() {
        loadKoinModules(postsModule())
    }

    private fun postsModule() : Module {
        return module {
            single<PostRepository> { PostRepositoryImpl(MockAPIService) }
        }
    }

}