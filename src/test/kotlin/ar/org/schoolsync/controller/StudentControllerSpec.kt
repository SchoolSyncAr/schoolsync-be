package ar.org.schoolsync.controller

import ar.org.schoolsync.dto.auth.AuthenticationRequest
import ar.org.schoolsync.dto.user.toDTO
import ar.org.schoolsync.model.EntityFactory
import ar.org.schoolsync.model.RegularUserBehavior
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.annotation.DisplayName
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
@DisplayName("Student Controller integration tests")

class StudentControllerSpec {
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
        userRepository.save(factory.createUser(Role.STUDENT))
    }

    @AfterEach
    fun `clear repositories`() {
        userRepository.deleteAll()
    }

    private fun getTokenForTestUser(): String {
        // You can implement this method to retrieve a token, e.g., by calling the authentication endpoint
        val authRequest = AuthenticationRequest("adminuser@schoolsync.mail.com", "adminuser")
        val jsonRequest = mapper.writeValueAsString(authRequest)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()

        val response = result.response.contentAsString
        val node = mapper.readTree(response)
        return node["accessToken"].asText()
    }

    @Test
    fun `Should create a new student`() {
        val token = getTokenForTestUser()

        val student = factory.createUser(Role.STUDENT).apply { email = "isma@gmail.com" }.toDTO()
        val jsonStudent = mapper.writeValueAsString(student)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/student")
                .header("Authorization", "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStudent)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("isma@gmail.com"))
    }

    @Test
    fun `Should return all students`() {
        val token = getTokenForTestUser()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/student/all")
                .header("Authorization", "Bearer $token")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))

    }
}