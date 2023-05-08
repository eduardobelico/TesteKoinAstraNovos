package br.com.chicorialabs.astranovos.data.services

import br.com.chicorialabs.astranovos.data.model.Post
import retrofit2.http.GET

/**
 * Interface responsável pela comunicação com a API web.
 */

interface SpaceFlightNewsService {

    /**
     * Endpoint que acessa uma lista de arquivos. Não possui parâmetros.
     */
    @GET("articles")
    suspend fun listPosts() : List<Post>

}