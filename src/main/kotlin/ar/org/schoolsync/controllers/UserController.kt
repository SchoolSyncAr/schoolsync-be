package ar.org.schoolsync.controllers

import ar.org.schoolsync.dto.UserLoginDTO
import ar.org.schoolsync.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
@Tag(name = "User", description = "User related operations")
class UserController(
    val userService: UserService
) {
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Devuelve un id en caso de login correcto"),
            ApiResponse(responseCode = "401", description = ar.org.schoolsync.domain.UserError.INVALID_CREDENTIALS),
            ApiResponse(responseCode = "404", description = ar.org.schoolsync.domain.RepositoryError.ELEMENT_NOT_FOUND),
            ApiResponse(responseCode = "501", description = ar.org.schoolsync.domain.Error.GENERAL_ERROR)
        ]
    )
    @PostMapping("login")
    @Operation(summary = "Retorna id de usuario al loguear correctamente")
    fun login(@RequestBody user: UserLoginDTO) = userService.login(user)
}