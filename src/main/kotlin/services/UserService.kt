package ar.org.schoolsync.services

import ar.org.schoolsync.exceptions.CreationError
import ar.org.schoolsync.exceptions.FindError
import ar.org.schoolsync.exceptions.ResponseFindException
import ar.org.schoolsync.exceptions.ResponseStatusException
import ar.org.schoolsync.model.AdminBehavior
import ar.org.schoolsync.model.ParentBehavior
import ar.org.schoolsync.model.StudentBehavior
import ar.org.schoolsync.model.User
import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
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

    fun findAllByRole(role: Role): List<User> = userRepository.findAllByRole(role).map { it }
    fun findByEmail(email: String): User? = userRepository.findByEmail(email).getOrNull()

    fun findMyChildren(parentId: Long): List<User> {
        val parent = findOrErrorByID(parentId)
        val myChildren = parent.childrens.map { it }
        return myChildren
    }

    fun allByGroup(group: NotificationGroup): List<User> {
        return findAll().filter {
            it.notificationGroups.contains(group)
        }
    }

    fun createAdmin(user: User) =
        save(user.apply { changeBehavior(AdminBehavior()) })

    fun createParent(user: User) =
        save(user.apply { changeBehavior(ParentBehavior()) })

    fun createStudent(user: User) =
        save(user.apply { changeBehavior(StudentBehavior()) })

    fun save(user: User): User {
        validateEmailExistance(user.email)
        encryptPassword(user)
        return userRepository.save(
            user
        )
    }

    fun deleteByID(id: Long): ResponseEntity<Boolean> {
        val found = Optional.ofNullable(findByID(id))

        return found.map {
            userRepository.delete(it)
            ResponseEntity.noContent().build<Boolean>()
        }.orElseThrow { ResponseFindException(FindError.USER_NOT_FOUND(id)) }
    }

    private fun validateEmailExistance(email: String) {
        findByEmail(email)?.let { throw ResponseStatusException(CreationError.CANNOT_CREATE_USER) }
    }

    private fun encryptPassword(user: User): User = user.apply { password = encoder.encode(password) }

    fun findParentsByGroup(notificationGroup: NotificationGroup):List<User> {
        return findAllByRole(Role.PARENT).filter { it.notificationGroups.contains(notificationGroup) }
    }
}