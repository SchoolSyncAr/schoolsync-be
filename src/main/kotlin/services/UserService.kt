package ar.org.schoolsync.services

import ar.org.schoolsync.exeptions.CreationError
import ar.org.schoolsync.exeptions.FindError
import ar.org.schoolsync.exeptions.ResponseFindException
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.users.User
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(private val userRepository: UserRepository, private val encoder: PasswordEncoder) {
    fun findByID(id: Long): User? = userRepository.findById(id).getOrNull()
    fun findOrErrorByID(id: Long): User =
        findByID(id) ?: throw ResponseFindException(FindError.USER_NOT_FOUND(id))
    fun findAll(): List<User> = userRepository.findAll().map { it }
    fun findByEmail(email: String): User? = userRepository.findByEmail(email).getOrNull()

    fun allByGroup(group: NotificationGroup): List<User> {
        return findAll().filter {
            it.notificationGroups.contains(group)
        }
    }

    fun save(user: User): User {
        val found = userRepository.findByEmail(user.email).getOrNull()

        return if (found == null) {
            user.password = encoder.encode(user.password)
            userRepository.save(user)
            user
        } else throw ResponseStatusException(CreationError.CANNOT_CREATE_USER)
    }

    fun deleteByID(id: Long): ResponseEntity<Boolean> {
        val found = Optional.ofNullable(findByID(id))

        return found.map {
            userRepository.delete(it)
            ResponseEntity.noContent().build<Boolean>()
        }.orElseThrow { ResponseFindException(FindError.USER_NOT_FOUND(id)) }
    }
}