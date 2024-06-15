package ar.org.schoolsync.controllers.user

import ar.org.schoolsync.dto.user.UserResponseDTO
import ar.org.schoolsync.dto.user.toCreateResponse
import ar.org.schoolsync.dto.user.toResponseStudent
import ar.org.schoolsync.model.User
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/student")
@Tag(name = "Student", description = "Student Api Operations")

class StudentController (
    @Autowired val userService: UserService
) {
    @PostMapping
    @Operation(summary = "Crea un nuevo student")
    fun create(@RequestBody student: User): UserResponseDTO {
        return userService.createStudent(student).toCreateResponse()
    }

    @GetMapping("/all")
    @Operation(summary = "Devuelve todos los estudiantes del colegio")
    fun findAll(): List<UserResponseDTO> =
        userService.findAllByRole(Role.STUDENT).map { it.toResponseStudent() }

}








