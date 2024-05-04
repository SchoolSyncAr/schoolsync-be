//package ar.org.schoolsync.controllers
//
//import ar.org.schoolsync.services.AuthenticationService
//import io.swagger.v3.oas.annotations.Operation
//import io.swagger.v3.oas.annotations.tags.Tag
//import org.springframework.web.bind.annotation.CrossOrigin
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
//@RequestMapping("\${route.base}/user")
//@Tag(name = "User Authentication", description = "User Authentication Api Operations")
//class AuthController(private val authenticationService: AuthenticationService)  {
//    @PostMapping
//    @Operation(summary = "User Authentication Process")
//    fun authenticate(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse =
//        authenticationService.authentication(authRequest)
//
//}

