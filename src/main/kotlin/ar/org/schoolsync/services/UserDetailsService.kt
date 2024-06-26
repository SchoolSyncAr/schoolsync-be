package ar.org.schoolsync.services

import ar.org.schoolsync.dto.user.toUserDetailsDTO
import ar.org.schoolsync.exceptions.InvalidAuthRequest
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            .getOrNull()
            ?.toUserDetailsDTO()
            ?: throw InvalidAuthRequest()
}