package jp.ac.titech.itsp.mercari.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.ac.titech.itsp.mercari.models.Chat
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ChatRepository
import jp.ac.titech.itsp.mercari.repositories.ChatRoomRepository
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import jp.ac.titech.itsp.mercari.services.ChatService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import javax.transaction.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ChatControllerTests {

    @Autowired
    lateinit var itemRepository: ItemRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var chatRepository: ChatRepository
    @Autowired
    lateinit var mvc: MockMvc

    lateinit var user: User

    @BeforeEach
    fun before() {
        user = userRepository.save(User("user"))
    }

    @Test
    @WithMockUser
    fun send() {
        val item = itemRepository.save(Item(user, "item"))
        val id = mvc.perform(
            post("/api/chat/${item.id}")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("message=hoge")
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString.toLong()
        assertTrue(chatRepository.existsById(id))
        assertEquals("hoge", chatRepository.getOne(id).message)
    }

    @Test
    fun getAll() {
        val item = itemRepository.save(Item(user, "item"))
        repeat(5) { chatRepository.save(Chat(item.publicRoom, user, "chat $it")) }
        val result = mvc.perform(get("/api/chat/${item.id}"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Chat> = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(5, actual.size.toLong())
    }

}