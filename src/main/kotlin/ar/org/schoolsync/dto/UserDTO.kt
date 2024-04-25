package ar.org.schoolsync.dto

import ar.org.schoolsync.domain.User


data class UserLoginDTO(val username: String, val password: String)

data class UserLoginResponseDTO(val id: Long)

fun User.loginResponse() = UserLoginResponseDTO(id = this.id)