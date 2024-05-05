package ar.org.schoolsync.controllers

import ar.org.schoolsync.model.User
import ar.org.schoolsync.dto.user.*
import ar.org.schoolsync.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/user")
@Tag(name = "User", description = "User Api Operations")
class UserController(@Autowired val userService: UserService) {

    @PostMapping
    @Operation(summary = "Crea un nuevo usuario")
    fun create(@RequestBody user: User): UserCreatedDTO {
        println("CREANDOOOOOOOOOOOO $user")
        return userService.save(user).toCreatedDTO()
    }

    @GetMapping("/all")
    @Operation(summary = "Retorna todos los usuarios del sistema")
    fun findAll(): List<UserResponseDTO> =
        userService.findAll().map { it.toResponseDTO() }

    @GetMapping("/{uuid}")
    @Operation(summary = "Retorna un usuario basado en su UUID")
    fun findByUUID(@PathVariable uuid: UUID): UserResponseDTO =
        userService.findOrErrorByUUID(uuid).toResponseDTO()

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Borra un usuario existente")
    fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> = userService.deleteByUUID(uuid)
}