package ar.org.schoolsync.controllers.user

import ar.org.schoolsync.dto.user.UserResponseDTO
import ar.org.schoolsync.dto.user.toCreateResponse
import ar.org.schoolsync.dto.user.toResponse
import ar.org.schoolsync.dto.user.toResponseParent
import ar.org.schoolsync.model.User
import ar.org.schoolsync.model.enums.Role
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
    @Autowired val userService: UserService
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
    fun findMyChildren(@PathVariable parentId: Long): List<UserResponseDTO>? {
        return userService.findMyChildren(parentId)?.map { it.toResponse() }
    }
}

