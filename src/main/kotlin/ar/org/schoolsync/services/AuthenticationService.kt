package ar.org.schoolsync.services

import ar.org.schoolsync.config.security.JwtProperties
import ar.org.schoolsync.dto.auth.AuthenticationRequest
import ar.org.schoolsync.dto.auth.AuthenticationResponse
import ar.org.schoolsync.exceptions.InvalidAuthRequest
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.security.core.AuthenticationException

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository

) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.email,
                    authRequest.password
                )
            )
        } catch (ex: AuthenticationException) {
            throw InvalidAuthRequest()
        }


        val userDetails = userDetailsService.loadUserByUsername(authRequest.email!!)
        val user = userRepository.findByEmail(userDetails.username).orElseThrow { InvalidAuthRequest() }
        val accessToken = tokenService.generate(
            userDetails = userDetails,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
            additionalClaims = mapOf("userId" to user.id, "role" to user.role)
        )

        return AuthenticationResponse(
            accessToken = accessToken
        )
    }
}