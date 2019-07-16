package jp.ac.titech.itsp.mercari.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import jp.ac.titech.itsp.mercari.repositories.UserRepository
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

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ItemControllerTests {

    @Autowired
    lateinit var itemRepository: ItemRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var mvc: MockMvc

    lateinit var user: User

    @BeforeEach
    fun before() {
        user = userRepository.save(User("user"))
    }

    @Test
    @WithMockUser
    fun registerItem() {
        val id = mvc.perform(
            post("/api/item")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("name=hoge")
        )
            .andExpect(status().isOk)
            .andReturn().response.contentAsString.toLong()
        assertTrue(itemRepository.existsById(id))
        assertEquals("hoge", itemRepository.getOne(id).name)

    }

    @Test
    fun getItem() {
        val item = itemRepository.save(Item("item", user))
        val result = mvc.perform(get("/api/item/${item.id}"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(item, actual)
    }

    @Test
    fun getItemNotFound() {
        mvc.perform(get("/api/item/114514"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun getItems() {
        repeat(5) { itemRepository.save(Item("item $it", user)) }
        val result = mvc.perform(get("/api/item"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(itemRepository.count(), actual.size.toLong())
    }

    @Test
    fun searchItem() {
        itemRepository.save(Item("searchable item", user))
        // TODO url encode
        val result = mvc.perform(get("/api/item/search?name=le it"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(1, actual.size)
    }

}
