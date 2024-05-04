package ar.org.schoolsync.services

import ar.org.schoolsync.domain.User
import ar.org.schoolsync.errors.CreationError
import ar.org.schoolsync.errors.FindError
import ar.org.schoolsync.errors.IdInvalido
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(@Autowired val userRepository: UserRepository) {
    fun save(user: User): User {
        val found = userRepository.findByEmail(user.email)
        return if (found == null) userRepository.save(user) else throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, CreationError.CANNOT_CREATE_USER
        )
    }

    fun findOrErrorByUUID(uuid: UUID): User = findByUUID(uuid) ?: throw IdInvalido(FindError.USER_NOT_FOUND(uuid))
    fun findByUUID(uuid: UUID): User? = userRepository.findById(uuid).getOrNull()
    fun findAll(): List<User> = userRepository.findAll().toList()
    fun deleteByUUID(uuid: UUID): ResponseEntity<Boolean> {
        val found = Optional.ofNullable(findByUUID(uuid))

        return found.map {
            userRepository.delete(it)
            ResponseEntity.noContent().build<Boolean>()
        }.orElseThrow { IdInvalido(FindError.USER_NOT_FOUND(uuid)) }
    }
}