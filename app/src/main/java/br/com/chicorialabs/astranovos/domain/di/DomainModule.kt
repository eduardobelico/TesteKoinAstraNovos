package br.com.chicorialabs.astranovos.domain.di

import br.com.chicorialabs.astranovos.domain.PostUseCases.GetLatestPostsTitleContainsUseCase
import br.com.chicorialabs.astranovos.domain.PostUseCases.GetLatestPostsUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetLatestPostsUseCase(get()) }
            factory { GetLatestPostsTitleContainsUseCase(get()) }
        }
    }
}