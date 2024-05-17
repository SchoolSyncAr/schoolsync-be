package ar.org.schoolsync.dto.user

import ar.org.schoolsync.model.Role
import ar.org.schoolsync.model.User
import java.util.*

data class UserResponseDTO (
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role
)

fun User.toResponseDTO() = UserResponseDTO(
    this.id,
    this.firstName,
    this.lastName,
    this.email,
    this.role
)