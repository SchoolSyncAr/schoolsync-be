package ar.org.schoolsync.controller

import ar.org.schoolsync.domain.Parent
import ar.org.schoolsync.domain.User
import ar.org.schoolsync.dto.UserLoginDTO
import ar.org.schoolsync.dto.loginResponse
import ar.org.schoolsync.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.annotation.DisplayName
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Test de Controller de Usuario")
internal class UserControllerSpec @Autowired constructor(val mockMvc: MockMvc, val mapper:ObjectMapper) {

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var parent: User

    @BeforeEach
    fun init() {
        userRepository.deleteAll()
        parent = Parent(firstName = "Pablo", lastName = "Foglia", username = "mad", password = "1234")
        userRepository.save(parent)
    }

    @Test
    fun `Logeo de usuario correcto`() {
        val userData = UserLoginDTO("mad", "1234")

        mockMvc.perform(post("/api/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(userData))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/json"))
            .andExpect(content().json(mapper.writeValueAsString(parent.loginResponse())))
    }

    @Test
    fun `Logeo de usuario incorrecto por contrasena`() {
        val userData = UserLoginDTO("Pablo", "12")

        mockMvc.perform(post("/api/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(userData))
        )
            .andExpect(status().is4xxClientError)
    }

    @Test
    fun `Logeo de usuario incorrecto por user inexistente`() {
        val userData = UserLoginDTO("Martin", "1234")

        mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(userData))
        )
            .andExpect(status().is4xxClientError)
    }
}