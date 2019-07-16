package jp.ac.titech.itsp.mercari.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.ac.titech.itsp.mercari.models.User
import jp.ac.titech.itsp.mercari.repositories.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var mvc: MockMvc

    @BeforeEach
    fun before() {
        userRepository.save(User("default-user"))
    }

    @Test
    fun registerUser() {
        mvc.perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=myid&password=mypass")
        )
            .andExpect(status().isOk)
    }

    @Test
    fun getUser() {
        val result = mvc.perform(get("/api/user/default-user"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: User = jacksonObjectMapper().readValue(result.response.contentAsString)
    }

    @Test
    fun getUserNotFound() {
        mvc.perform(get("/api/user/notexist"))
            .andExpect(status().isNotFound)
    }

}
