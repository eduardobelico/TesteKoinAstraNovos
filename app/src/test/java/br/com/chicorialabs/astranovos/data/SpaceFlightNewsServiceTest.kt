package br.com.chicorialabs.astranovos.data

import br.com.chicorialabs.astranovos.data.remote.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class SpaceFlightNewsServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var service: SpaceFlightNewsService

    @Before
    fun createService() {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun deve_AlcancarEndpointCorreto_AoReceberParametro() {
        runBlocking {
            //testar o endpoint articles
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val result1 = service.listPosts(SpaceFlightNewsCategory.ARTICLES.value)
            val request1 = mockWebServer.takeRequest()
            assertEquals(request1.path, "/articles")

            //testar o endpoint blogs
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val result2 = service.listPosts(SpaceFlightNewsCategory.BLOGS.value)
            val request2 = mockWebServer.takeRequest()
            assertEquals(request2.path, "/blogs")

            //testar o endpoint reports
            mockWebServer.enqueue(MockResponse().setBody("[]"))
            val result3 = service.listPosts(SpaceFlightNewsCategory.REPORTS.value)
            val request3 = mockWebServer.takeRequest()
            assertEquals(request3.path, "/reports")

        }
    }

    /**
     * Verificar se o endpoint correto é alcançado.
     */

    @Test
    fun deve_AlcancarEnpointCorreto_AoReceberQueryComOption() {
        runBlocking {
            mockWebServer.enqueue((MockResponse().setBody("[]")))
            service.listPostsTitleContains("articles", "mars")
            val request = mockWebServer.takeRequest()
            println(request.path)
            assertEquals(request.path, "/articles?title_contains=mars")
        }
    }

    @Test
    fun deve_AlcancarEnpointCorreto_AoReceberQueryComOptionNull() {
        runBlocking {
            mockWebServer.enqueue((MockResponse().setBody("[]")))
            service.listPostsTitleContains("articles", null)
            val request = mockWebServer.takeRequest()
            println(request.path)
            assertEquals(request.path, "/articles")
        }
    }

}