package br.com.chicorialabs.astranovos.core

import kotlinx.coroutines.flow.Flow

/**
 * Essa classe define um usecase genérico. Por enquanto sem parâmetros.
 */
abstract class UseCase<Source> {

    abstract suspend fun execute() : Flow<Source>
}