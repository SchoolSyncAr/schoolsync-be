package ar.org.schoolsync.dto.user

import ar.org.schoolsync.model.User

data class UserResponseDTO(
    val id: Long,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val absences: Int? = null,
    val notificationGroups: List<String>? = null
)

fun User.toCreateResponse() = UserResponseDTO(
    id,
    email = email
)

fun User.toResponse() = UserResponseDTO(
    id,
    firstName,
    lastName,
    email
)

fun User.toResponseParent() = UserResponseDTO(
    id,
    firstName,
    lastName,
    notificationGroups = notificationGroups.map { it.name }
)

fun User.toResponseStudent() = UserResponseDTO(
    id,
    firstName,
    lastName,
    absences = absences
)