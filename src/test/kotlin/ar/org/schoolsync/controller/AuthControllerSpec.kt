package ar.org.schoolsync.controller

import ar.org.schoolsync.dto.auth.AuthenticationRequest
import ar.org.schoolsync.model.EntityFactory
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.model.utils.JWTDecoder
import ar.org.schoolsync.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Auth controller integration tests")
class AuthControllerSpec {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mapper: ObjectMapper


    val factory = EntityFactory()

    @BeforeEach
    fun `init user repository`() {
        userRepository.save(factory.createUser(Role.ADMIN))
        userRepository.save(factory.createUser(Role.PARENT))
    }

    @AfterEach
    fun `clear repositories`() {
        userRepository.deleteAll()
    }

    @Test
    fun `Should return a valid access token on valid credentials`() {
        val request = AuthenticationRequest("adminuser@schoolsync.mail.com","adminuser")
        val jsonRequest = mapper.writeValueAsBytes(request)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty)
    }

    @Test
    fun `Should return a error on invalid credentials`() {
        val badUserRequest = AuthenticationRequest("baduser@email.com","adminuser")
        var jsonRequest = mapper.writeValueAsBytes(badUserRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized())

        val badPasswordRequest = AuthenticationRequest("adminuser@schoolsync.mail.com","1234")
        jsonRequest = mapper.writeValueAsBytes(badPasswordRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
    }

    @Test
    fun `Should return a valid access token for Parent User`() {
        val request = AuthenticationRequest("parent@schoolsync.mail.com","parentuser")
        val jsonRequest = mapper.writeValueAsBytes(request)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()

        val accessToken = result.response.contentAsString.let {
            val node = mapper.readTree(it)
            node.get("accessToken").asText()
        }

        JWTDecoder(accessToken)
            .setClaims(setOf("role"))
            .build()["role"] shouldBe "PARENT"
    }

    @Test
    fun `Should return a valid access token for Admin User`() {
        val request = AuthenticationRequest("adminuser@schoolsync.mail.com","adminuser")
        val jsonRequest = mapper.writeValueAsBytes(request)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()

        val accessToken = result.response.contentAsString.let {
            val node = mapper.readTree(it)
            node.get("accessToken").asText()
        }

        JWTDecoder(accessToken)
            .setClaims(setOf("role"))
            .build()["role"] shouldBe "ADMIN"
    }
}