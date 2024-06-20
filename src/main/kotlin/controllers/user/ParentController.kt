package ar.org.schoolsync.controllers.user

import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.toDTO
import ar.org.schoolsync.dto.user.UserResponseDTO
import ar.org.schoolsync.dto.user.toCreateResponse
import ar.org.schoolsync.dto.user.toResponse
import ar.org.schoolsync.dto.user.toResponseParent
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.model.User
import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.services.NotificationRegistryService
import ar.org.schoolsync.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/parent")
@Tag(name = "Parent", description = "Parent Api Operations")
class ParentController(
    @Autowired val userService: UserService,
    @Autowired val notificationRegistryService: NotificationRegistryService
) {
    @PostMapping()
    @Operation(summary = "Crea un nuevo padre")
    fun create(@RequestBody parent: User): UserResponseDTO {
        return userService.createParent(parent).toCreateResponse()
    }

    @GetMapping("/all")
    @Operation(summary = "Retorna todos los padres del sistema")
    fun findAll(): List<UserResponseDTO> =
        userService.findAllByRole(Role.PARENT).map { it.toResponseParent() }

    @GetMapping("/childs/{parentId}")
    @Operation(summary = "devuelve todos los hijos de un determinado padre")
    fun findMyChildren(@PathVariable parentId: Long): List<UserResponseDTO> {
        return userService.findMyChildren(parentId).map { it.toResponse() }
    }

    @GetMapping("/getByGroup")
    @Operation(summary = "devuelve todos los padres de un determinado curso")
    fun findParentsByNotificationGroup(
        @RequestParam notificationGroup: NotificationGroup
    ): List<UserResponseDTO> =
        userService.findParentsByGroup(notificationGroup).map { it.toResponse() }

    @GetMapping("/{parentId}/notifications")
    @Operation(summary = "devuelve todos las notificaciones de un padre por id")
    fun findNotificationsById(
        @PathVariable parentId: Long,
        @RequestParam searchField: String,
        @RequestParam sortField: String,
    ): List<NotificationDTO> {
        return notificationRegistryService.findAllByUserId(parentId, SearchFilter(searchField, sortField))
            .map { it.toDTO() }

    }
}

