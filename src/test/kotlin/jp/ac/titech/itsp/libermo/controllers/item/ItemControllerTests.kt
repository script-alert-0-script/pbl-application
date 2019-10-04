package jp.ac.titech.itsp.libermo.controllers.item

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.ac.titech.itsp.libermo.models.Item
import jp.ac.titech.itsp.libermo.models.ItemState
import jp.ac.titech.itsp.libermo.models.User
import jp.ac.titech.itsp.libermo.repositories.ItemRepository
import jp.ac.titech.itsp.libermo.repositories.UserRepository
import org.junit.jupiter.api.Assertions.*
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
    private lateinit var itemRepository: ItemRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var mvc: MockMvc

    private lateinit var user: User

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
        val item = itemRepository.save(Item(user, "item"))
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
        repeat(5) { itemRepository.save(Item(user, "item $it")) }
        val result = mvc.perform(get("/api/item"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(itemRepository.count(), actual.size.toLong())
    }

    @Test
    fun searchItem() {
        itemRepository.save(Item(user, "searchable item"))
        val result = mvc.perform(get("/api/item/search?name=le it"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(1, actual.size)
    }

    @Test
    @WithMockUser
    fun request() {
        val other = userRepository.save(User("other", "other"))
        val item = itemRepository.save(Item(other, "item"))
        val result = mvc.perform(post("/api/item/${item.id}/request"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(ItemState.PENDING, actual.state)
        assertEquals(user.id, actual.buyer?.id)
    }

    @Test
    @WithMockUser
    fun cancel() {
        val other = userRepository.save(User("other", "other"))
        val item = itemRepository.save(Item(user, "item", ItemState.PENDING, other))
        val result = mvc.perform(post("/api/item/${item.id}/cancel"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(ItemState.AVAILABLE, actual.state)
        assertNull(actual.buyer)
    }

    @Test
    @WithMockUser
    fun allow() {
        val other = userRepository.save(User("other", "other"))
        val item = itemRepository.save(Item(user, "item", ItemState.PENDING, other))
        val result = mvc.perform(post("/api/item/${item.id}/allow"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(ItemState.COMPLETED, actual.state)
    }

}