package br.com.chicorialabs.astranovos.tests

import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.domain.PostUseCases.GetLatestPostsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class GetLatestPostsUseCaseTest : KoinTest {

    val getLatestPostsUseCase: GetLatestPostsUseCase by inject()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setup() {
            configTestAppComponent()
        }

        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }

    @Test
    fun deve_RetornarResultadoNaoNulo_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))

            assertFalse(result.first().isEmpty())
            assertTrue(result is Flow<List<Post>>)
            assertNotNull(result)

        }
    }

    @Test
    fun deve_RetornarObjetoDoTipoCorreto_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))

            assertTrue(result is Flow<List<Post>>)

        }
    }

    @Test
    fun deve_RetornarResultadoNaoVazio_AoConectarComRepositorio() {
        runBlocking {
            val result = getLatestPostsUseCase(Query(SpaceFlightNewsCategory.ARTICLES.value))

            assertFalse(result.first().isEmpty())

        }
    }

}