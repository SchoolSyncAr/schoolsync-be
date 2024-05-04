package ar.org.schoolsync.dto

data class AuthRequestDTO(
    val email: String,
    val password: String
)

data class AuthResponseDTO(
    val accessToken: String
)