package br.com.chicorialabs.astranovos.data.mappers

import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.remote.dtos.PostDto

fun PostDto.toPost() : Post {
    return Post(
        id = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        launches = launches.map { it.provider })
}
