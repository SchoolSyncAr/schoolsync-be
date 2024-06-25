package ar.org.schoolsync.controller.user

import ar.org.schoolsync.dto.auth.AuthenticationRequest
import ar.org.schoolsync.dto.auth.AuthenticationResponse
import ar.org.schoolsync.model.EntityFactory
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.annotation.DisplayName
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Parent controller integration tests")
class ParentControllerSpec {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mapper: ObjectMapper

    val factory = EntityFactory()
    private var encoder = BCryptPasswordEncoder()
    lateinit var token: String

    @BeforeEach
    fun `init user repository`() {
        userRepository.save(factory.createUser(Role.ADMIN))
        userRepository.save(factory.createUser(Role.PARENT))
        val parent2 = factory.createUser(Role.PARENT).apply {
            phoneNumber = "99999999"
            email = "parent2@schoolsync.mail.com" }
        userRepository.save(parent2)

        // Esto es necesario para que en cada test se autorize a un usuario a acceder.
        val authRequest = AuthenticationRequest("adminuser@schoolsync.mail.com", "adminuser")
        val authResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val authResponse: AuthenticationResponse = mapper.readValue(
            authResult.response.contentAsString,
            AuthenticationResponse::class.java
        )

        token = authResponse.accessToken
    }

    @AfterEach
    fun `clear repositories`() {
        userRepository.deleteAll()
    }

    @Test
    fun `Should return all parents from the system`() {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/parent/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$", hasSize<Any>(2)))
    }
}
