package br.com.chicorialabs.astranovos.data.services

import br.com.chicorialabs.astranovos.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface responsável pela comunicação com a API web.
 */

interface SpaceFlightNewsService {

    /**
     * Endpoint que acessa uma lista de arquivos.
     */

    @GET("{type}")
    suspend fun listPosts(@Path("type") type: String) : List<Post>

}