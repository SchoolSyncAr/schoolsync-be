//package ar.org.schoolsync.services
//
//import ar.org.schoolsync.ar.org.schoolsync.controllers.AuthenticationResponse
//import ar.org.schoolsync.all.controllers.AuthenticationRequest
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.token.TokenService
//import org.springframework.stereotype.Service
//import java.util.*
//
//
//@Service
//class AuthenticationService(
//    private val authManager: AuthenticationManager,
//    private val userService: UserService,
//    private val tokenService: TokenService,
//    private val jwtProperties: JwtProperties
//    ) {
//    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
//        authManager.authenticate(
//            UsernamePasswordAuthenticationToken(
//                authRequest.email,
//                authRequest.password
//            )
//        )
//
//        val user = userService.findByUsername(authRequest.email)
//        val accessToken = tokenService.generate(
//            userDetails = user,
//            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
//        )
//
//        return AuthenticationResponse(
//            accessToken = accessToken
//        )
//    }
//}