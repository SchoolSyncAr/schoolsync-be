package ar.org.schoolsync.services

import ar.org.schoolsync.exeptions.CreationError
import ar.org.schoolsync.exeptions.FindError
import ar.org.schoolsync.exeptions.ResponseFindException
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.model.User
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(private val userRepository: UserRepository, private val encoder: PasswordEncoder) {
    fun save(user: User): User {
        val found = userRepository.findByEmail(user.email).getOrNull()

        return if (found == null) {
            val updated = user.copy(password = encoder.encode(user.password))
            userRepository.save(updated)
            updated
        } else throw ResponseStatusException(CreationError.CANNOT_CREATE_USER)
    }

    fun findOrErrorByUUID(uuid: UUID): User =
        findByUUID(uuid) ?: throw ResponseFindException(FindError.USER_NOT_FOUND(uuid))

    fun findByEmail(email: String): User? = userRepository.findByEmail(email).getOrNull()

    fun findByUUID(uuid: UUID): User? = userRepository.findById(uuid).getOrNull()
    fun findAll(): List<User> = userRepository.findAll().map { it }
    fun deleteByUUID(uuid: UUID): ResponseEntity<Boolean> {
        val found = Optional.ofNullable(findByUUID(uuid))

        return found.map {
            userRepository.delete(it)
            ResponseEntity.noContent().build<Boolean>()
        }.orElseThrow { ResponseFindException(FindError.USER_NOT_FOUND(uuid)) }
    }
}