package ar.org.schoolsync.controller.user

import ar.org.schoolsync.dto.auth.AuthenticationRequest
import ar.org.schoolsync.dto.user.UserResponseDTO
import ar.org.schoolsync.model.EntityFactory
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.repositories.UserRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.annotation.DisplayName
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.hibernate.validator.internal.util.Contracts.assertTrue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
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
@DisplayName("Parent controller integration tests")
class ParentControllerSpec {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mapper: ObjectMapper


    val factory = EntityFactory()


    @BeforeEach
    fun `init user repository`() {
        AuthenticationRequest("adminuser@schoolsync.mail.com","adminuser")
        userRepository.save(factory.createUser(Role.ADMIN))
        userRepository.save(factory.createUser(Role.PARENT))
    }

    @AfterEach
    fun `clear repositories`() {
        userRepository.deleteAll()
    }

    @Test
    fun `Should return all parents from the system`() {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/parent/all")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val responseBody = result.response.contentAsString
        val responseList: List<UserResponseDTO> = mapper.readValue(
            responseBody,
            object : TypeReference<List<UserResponseDTO>>() {}
        )

        assertNotNull(responseList)
//        assertTrue(responseList.isNotEmpty())

        responseList.forEach { dto ->
            assertNotNull(dto.email)
            assertNotNull(dto.id)
        }

    }
}