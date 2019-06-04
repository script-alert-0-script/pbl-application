package jp.ac.titech.itsp.mercari.controllers

import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.*
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTests {

    @Autowired
    lateinit var itemRepository: ItemRepository
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @BeforeEach
    fun before() {
        itemRepository.saveAll((1..5L).map { Item("name$it", "user$it", it) })
    }

    @Test
    fun register() {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
        val actual = testRestTemplate.postForEntity<String>(
            "/api/item",
            HttpEntity("""name=hoge&user=poyo""", headers)
        )
        println(actual.statusCode)
    }

    @Test
    fun get() {
        val actual: ResponseEntity<Item> = testRestTemplate.getForEntity("/api/item/1")
        assertEquals(HttpStatus.OK, actual.statusCode)
    }

    @Test
    fun getNotFound() {
        val actual: ResponseEntity<Item> = testRestTemplate.getForEntity("/api/item/114514")
        assertEquals(HttpStatus.NOT_FOUND, actual.statusCode)
    }

    @Test
    fun getAll() {
        val actual: ResponseEntity<List<Item>> = testRestTemplate.getForEntity("/api/item")
        assertEquals(HttpStatus.OK, actual.statusCode)
    }

}
