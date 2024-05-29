package ar.org.schoolsync.dto.auth

import ar.org.schoolsync.model.Role

data class AuthenticationResponse(
    val accessToken: String,
    val role: Role,
    val userId: Long
)