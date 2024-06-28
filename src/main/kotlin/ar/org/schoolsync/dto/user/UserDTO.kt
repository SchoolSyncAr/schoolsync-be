package ar.org.schoolsync.dto.user

import ar.org.schoolsync.model.User

data class UserDTO(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var password: String
)

fun UserDTO.fromJson() = User(
    firstName,
    lastName,
    email,
    phoneNumber,
    password
)

fun User.toDTO() = UserDTO(
    firstName,
    lastName,
    email,
    phoneNumber,
    password
)