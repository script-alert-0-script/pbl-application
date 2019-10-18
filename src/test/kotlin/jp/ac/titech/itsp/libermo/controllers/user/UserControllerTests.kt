package jp.ac.titech.itsp.libermo.controllers.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.ac.titech.itsp.libermo.models.User
import jp.ac.titech.itsp.libermo.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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
class UserControllerTests {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun registerUser() {
        val result = mvc.perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=my-id&name=my-name&password=my-pass")
        )
            .andExpect(status().isOk)
            .andReturn()
        val actual: User = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals("my-id", actual.id)
    }

    @Test
    fun getUser() {
        val user = userRepository.save(User("test-id", "test-name", "test-pass"))
        val result = mvc.perform(get("/api/user/test-id"))
            .andExpect(status().isOk)
            .andReturn()
        val actual: User = jacksonObjectMapper().readValue(result.response.contentAsString)
        assertEquals(user.id, actual.id)
    }

    @Test
    fun getUserNotFound() {
        mvc.perform(get("/api/user/not-exist"))
            .andExpect(status().isNotFound)
    }

}
