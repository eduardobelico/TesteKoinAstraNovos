package br.com.chicorialabs.astranovos.domain.PostUseCases

import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.tests.configTestAppComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

internal class GetLatestPostsTitleContainsUseCaseTest : KoinTest {

    val getLatestPostsTitleContainsUseCase: GetLatestPostsTitleContainsUseCase by inject()
    private val type = SpaceFlightNewsCategory.ARTICLES.value
    private val searchString = "mars"

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
    fun deve_RetornarResultadosValidos_AoExecutarBusca() {
        runBlocking {
            val result = getLatestPostsTitleContainsUseCase(Query(type, searchString))
            var assertion = true
            result.first().forEach{ post ->
                assertion = assertion && post.title.lowercase().contains(searchString)
            }
            assertTrue(assertion)
        }
    }
}