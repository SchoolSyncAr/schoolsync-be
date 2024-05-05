package ar.org.schoolsync.controllers

import ar.org.schoolsync.dto.auth.AuthenticationRequest
import ar.org.schoolsync.dto.auth.AuthenticationResponse
import ar.org.schoolsync.services.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/auth")
@Tag(name = "User Authentication", description = "User Authentication Api Operations")
class AuthController(private val authenticationService: AuthenticationService) {
    @PostMapping
    @Operation(summary = "User Authentication Process")
    fun authenticate(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse =
        authenticationService.authentication(authRequest)
}

