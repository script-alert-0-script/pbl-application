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
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
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
        user = userRepository.save(User(TEST_ID, TEST_EMAIL))
    }

    @Test
    @WithMockUser(TEST_ID)
    fun registerItem() {
        val image = MockMultipartFile("image", "dummy.png", "image/png", ClassPathResource("dummy.png").inputStream)
        val id = mvc.perform(
            multipart("/api/item")
                .file(image)
                .param("name", "hoge")
                .param("author", "foo")
                .param("description", "bar")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
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
    @WithMockUser(TEST_ID)
    fun getItemNotFound() {
        mvc.perform(get("/api/item/114514"))
            .andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(TEST_ID)
    fun getItems() {
        repeat(5) { itemRepository.save(Item(user, "item $it")) }
        val result = mvc.perform(get("/api/item"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(itemRepository.count(), actual.size.toLong())
    }

    @Test
    @WithMockUser(TEST_ID)
    fun searchItem() {
        itemRepository.save(Item(user, "searchable item"))
        val result = mvc.perform(get("/api/item/search?name=le it"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: List<Item> = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(1, actual.size)
    }

    @Test
    @WithMockUser(TEST_ID)
    fun request() {
        val other = userRepository.save(User("other", "other"))
        val item = itemRepository.save(Item(other, "item"))
        val result = mvc.perform(
            post("/api/item/${item.id}/request")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(ItemState.PENDING, actual.state)
        assertEquals(user.id, actual.buyer?.id)
    }

    @Test
    @WithMockUser(TEST_ID)
    fun refuse() {
        val other = userRepository.save(User("other", "other"))
        val item = itemRepository.save(Item(user, "item", "author", "desc", ItemState.PENDING, other))
        val result = mvc.perform(
            post("/api/item/${item.id}/refuse")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(ItemState.AVAILABLE, actual.state)
        assertNull(actual.buyer)
    }

    @Test
    @WithMockUser(TEST_ID)
    fun allow() {
        val other = userRepository.save(User("other", "other"))
        val item = itemRepository.save(Item(user, "item", "author", "desc", ItemState.PENDING, other))
        val result = mvc.perform(
            post("/api/item/${item.id}/allow")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
            .andExpect(status().isOk)
            .andReturn()
        val actual: Item = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(ItemState.COMPLETED, actual.state)
    }

    @Test
    @WithMockUser(TEST_ID)
    fun cancel() {
        val item = itemRepository.save(Item(user, "item", "author", "desc"))
        mvc.perform(
            post("/api/item/${item.id}/cancel")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
            .andExpect(status().isOk)
        assertFalse(itemRepository.existsById(item.id))
    }

    companion object {
        const val TEST_ID = "test-user"
        const val TEST_EMAIL = "test@m.titech.ac.jp"
    }

}
