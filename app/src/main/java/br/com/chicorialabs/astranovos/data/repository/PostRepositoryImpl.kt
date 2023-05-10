package br.com.chicorialabs.astranovos.data.repository

import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.data.mappers.toPost
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.remote.SpaceFlightNewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


/**
 * Essa classe implementa a interface PostRepository. Os dados são retornados na forma de um flow.
 */

class PostRepositoryImpl(private val service: SpaceFlightNewsService) : PostRepository {

    /**
     * Essa função usa o construtor flow { } para emitir a lista de Posts
     * na forma de um fluxo de dados. No uso real é preciso usar um bloco try-catch para
     * lidar com exceções no acesso à API.
     */
    override suspend fun listPosts(category: String): Flow<List<Post>> = flow {

        try {
            val postList = service.listPosts(type = category).map { listPostDto ->
                listPostDto.toPost()
            }
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to connect to SpaceFlightNews API")
        }

    }

    override suspend fun listPostsTitleContains(category: String, titleContains: String?): Flow<List<Post>> = flow {

        try {
            val postList = service.listPostsTitleContains(type = category, titleContains = titleContains).map { listPostDto ->
                listPostDto.toPost()
            }
            emit(postList)
        } catch (ex: HttpException) {
            throw RemoteException("Unable to retrieve posts!")
        }
    }
}
