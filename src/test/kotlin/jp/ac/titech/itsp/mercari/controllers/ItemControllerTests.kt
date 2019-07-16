package jp.ac.titech.itsp.mercari.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.models.ItemState
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
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

    @BeforeEach
    fun before() {
        val user = userRepository.save(User("user"))
        itemRepository.saveAll((1..5L).map { Item("name$it", user) })
    }

    @Test
    @WithMockUser
    fun registerItem() {
        mvc.perform(
            post("/api/item")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("name=hoge")
        )
            .andExpect(status().isOk)
    }

    @Test
    fun getItem() {
        val result = mvc.perform(get("/api/item/1"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(ItemState.AVAILABLE, actual.state)
    }

    @Test
    fun getItemNotFound() {
        mvc.perform(get("/api/item/114514"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun getItems() {
        val result = mvc.perform(get("/api/item"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
    }

    @Test
    fun searchItem() {
        val result = mvc.perform(get("/api/item/search?name=am"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
    }

}
