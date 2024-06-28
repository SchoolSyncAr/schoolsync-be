package ar.org.schoolsync.controllers.user

import ar.org.schoolsync.dto.user.*
import ar.org.schoolsync.model.User
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/admin")
@Tag(name = "Admin", description = "Admin Api Operations")
class AdminController(@Autowired val userService: UserService) {
    @PostMapping
    @Operation(summary = "Crea un nuevo administrador")
    fun create(@RequestBody user: User): UserResponseDTO {
        return userService.createAdmin(user).toCreateResponse()
    }

    @GetMapping("/all")
    @Operation(summary = "Retorna todos los admins del sistema")
    fun findAll(): List<UserResponseDTO> =
        userService.findAllByRole(Role.ADMIN).map { it.toResponse() }
}