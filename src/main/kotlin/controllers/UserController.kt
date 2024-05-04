package ar.org.schoolsync.controllers

import ar.org.schoolsync.domain.User
import ar.org.schoolsync.dto.UserCreateDTO
import ar.org.schoolsync.dto.UserDTO
import ar.org.schoolsync.dto.toCreateDTO
import ar.org.schoolsync.dto.toDTO
import ar.org.schoolsync.errors.FindError
import ar.org.schoolsync.errors.IdInvalido
import ar.org.schoolsync.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
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
    fun save(@RequestBody userRequest: User): UserCreateDTO =
        userService.save(userRequest).toCreateDTO()

    @GetMapping("/all")
    @Operation(summary = "Retorna todos los usuarios del sistema")
    fun findAll(): List<UserDTO> =
        userService.findAll().map { it.toDTO() }

    @GetMapping("/{uuid}")
    @Operation(summary = "Retorna un usuario basado en su UUID")
    fun findByUUID(@PathVariable uuid: UUID): UserDTO =
        userService.findOrErrorByUUID(uuid).toDTO()

    @DeleteMapping
    @Operation(summary = "Borra un usuario existente")
    fun deleteByUUID(@RequestParam uuid: UUID): ResponseEntity<Boolean> = userService.deleteByUUID(uuid)
}