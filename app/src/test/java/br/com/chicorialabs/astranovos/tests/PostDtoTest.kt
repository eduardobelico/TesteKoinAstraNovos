package br.com.chicorialabs.astranovos.tests

import br.com.chicorialabs.astranovos.data.mappers.toPost
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.remote.dtos.LaunchDto
import br.com.chicorialabs.astranovos.data.remote.dtos.PostDto
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class PostDtoTest {

    //converte em entidade de modelo

    val launchDto = LaunchDto(
        id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
        provider = "Launch Library 2"
    )

    val postDto = PostDto(
        id = 12782,
        title = "Crew-3 mission cleared for launch",
        url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
        imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
        summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
        publishedAt = "2021-11-10T09:27:02.000Z",
        updatedAt = "2021-11-10T09:38:23.654Z",
        launches = listOf(launchDto)
    )

    @Test
    fun deve_ConverterCorretamenteEmEntidadeDeModelo() {
        val post: Post = postDto.toPost()

        assertTrue(post is Post)
        assertTrue(post.title == postDto.title)
        assertTrue(post.launches.isNotEmpty())


    }
}