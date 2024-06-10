package ar.org.schoolsync.dto.user

import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.model.users.User

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