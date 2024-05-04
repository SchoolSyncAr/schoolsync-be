package ar.org.schoolsync.dto

import ar.org.schoolsync.domain.User
import java.util.*

data class UserCreateDTO(
    val id: UUID,
    val email: String,
)

data class UserDTO(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
)

fun User.toCreateDTO() = UserCreateDTO(
    this.id,
    this.email
)

fun User.toDTO() = UserDTO(
    this.id,
    this.firstName,
    this.lastName,
    this.email
)

