package br.com.chicorialabs.astranovos.data.remote.dtos

data class PostDto(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    var launches: List<LaunchDto>
)
