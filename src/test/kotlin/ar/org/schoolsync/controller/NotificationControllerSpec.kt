package ar.org.schoolsync.controller

import ar.org.schoolsync.dto.auth.AuthenticationRequest
import ar.org.schoolsync.dto.auth.AuthenticationResponse
import ar.org.schoolsync.dto.notification.CreateNotificationDTO
import ar.org.schoolsync.model.EntityFactory
import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.NotificationPriorities
import ar.org.schoolsync.model.enums.NotificationType
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.repositories.NotificationRepository
import ar.org.schoolsync.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.annotation.DisplayName
import jakarta.transaction.Transactional
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
import io.kotest.matchers.shouldBe
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Notification Controller integration tests")
class NotificationControllerSpec {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var notificationRepository: NotificationRepository

    @Autowired
    private lateinit var mapper: ObjectMapper

    val factory = EntityFactory()
    lateinit var token: String

    @BeforeEach
    fun `init repository`() {
        val admin = userRepository.save(factory.createUser(Role.ADMIN))
        val parent = userRepository.save(factory.createUser(Role.PARENT))
        val patriotic = notificationRepository.save(factory.createNotification(NotificationType.PATRIOTIC, admin))
        val reunion = notificationRepository.save(factory.createNotification(NotificationType.REUNION, admin))

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
        notificationRepository.deleteAll() // Limpieza después de la prueba
        userRepository.deleteAll() // Limpieza después de la prueba
    }

    @Test
    @Transactional
    fun `Should create a new notification`() {
        val admin = userRepository.findByEmail("adminuser@schoolsync.mail.com").get()

        val request = CreateNotificationDTO(
            sender = admin.id,
            recipientGroups = listOf(NotificationGroup.GRADO2),
            title = "Test Notification",
            content = "This is a test notification",
            weight = NotificationPriorities.ALTA.toString()
        )
        val jsonRequest = mapper.writeValueAsBytes(request)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/notification/create")
                .header("Authorization", "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Notification"))
            .andReturn()
        val json = notificationRepository.findDistinctByTitleAndContent("Test Notification","This is a test notification")
        json.first().weight shouldBe NotificationPriorities.ALTA
    }

    @Test
    fun `Should get all notifications`() {
        val searchField = "acto"
        val sortField = ""

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/notification/all")
                .header("Authorization", "Bearer $token")
                .param("searchField", searchField)
                .param("sortField", sortField)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Acto 25 de mayo"))
    }

    @Test
    fun shouldGetUnreadNotificationsCount() {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/notification/count")
                .header("Authorization", "Bearer $token")
                .param("userId", 1L.toString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("0"))
    }

    @Test
    @WithMockUser(username = "adminuser@schoolsync.mail.com", roles = ["ADMIN"])
    fun `Should get recipient groups as admin`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/notification/recipient-groups")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(NotificationGroup.values().size))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(NotificationGroup.TODOS.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(NotificationGroup.PADRES.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2]").value(NotificationGroup.PERSONAL.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[3]").value(NotificationGroup.ESTUDIANTES.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[4]").value(NotificationGroup.GRADO1.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[5]").value(NotificationGroup.GRADO2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[6]").value(NotificationGroup.GRADO3.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[7]").value(NotificationGroup.GRADO4.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[8]").value(NotificationGroup.GRADO5.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[9]").value(NotificationGroup.GRADO6.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[10]").value(NotificationGroup.GRADO7.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[11]").value(NotificationGroup.EQUIPO_FUTBOL.name))
    }

    @Test
    @WithMockUser(username = "adminuser@schoolsync.mail.com", roles = ["ADMIN"])
    fun `Should get notification priorities as admin`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/notification/priorities")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(NotificationPriorities.values().size))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(NotificationPriorities.BAJA.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(NotificationPriorities.MEDIA.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2]").value(NotificationPriorities.ALTA.name))
    }
}
