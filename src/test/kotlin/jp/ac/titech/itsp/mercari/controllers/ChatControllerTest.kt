package jp.ac.titech.itsp.mercari.controllers

import jp.ac.titech.itsp.mercari.models.Item
import jp.ac.titech.itsp.mercari.repositories.ItemRepository
import org.junit.jupiter.api.Assertions
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
class ChatControllerTests {
    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @BeforeEach
    fun before() {
        itemRepository.saveAll((1..5L).map { Item("name$it", "user$it", mutableListOf(), it) })
    }

    @Test
    fun send() {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
        val actual = testRestTemplate.postForEntity<String>(
                "/api/chat/0",
                HttpEntity("""message=hoge&user=poyo""", headers)
        )
        Assertions.assertEquals(HttpStatus.OK, actual.statusCode)
    }

    @Test
    fun getAll() {
        val actual: ResponseEntity<Item> = testRestTemplate.getForEntity("/api/chat/0")
        Assertions.assertEquals(HttpStatus.OK, actual.statusCode)
    }

}