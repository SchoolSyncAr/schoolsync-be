package ar.org.schoolsync.services

import ar.org.schoolsync.dto.UserLoginDTO
import ar.org.schoolsync.dto.UserLoginResponseDTO
import ar.org.schoolsync.dto.loginResponse
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun login(user: UserLoginDTO): UserLoginResponseDTO {
        val storedUser = userRepository.findAll()
            .find { it.username == user.username && it.password == user.password }
            ?: throw ar.org.schoolsync.domain.AuthenticationException(ar.org.schoolsync.domain.UserError.INVALID_CREDENTIALS)

        return storedUser.loginResponse()
    }
}
