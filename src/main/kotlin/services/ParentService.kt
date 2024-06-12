package ar.org.schoolsync.services

import ar.org.schoolsync.exeptions.PersonCreationError
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.model.users.Parent
import ar.org.schoolsync.model.users.User
import ar.org.schoolsync.repositories.ParentRepository
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ParentService (private val userRepository: UserRepository,
                     private val parentRepository: ParentRepository,
                     private val encoder: PasswordEncoder
                     ) {
    fun save(parent: Parent): Parent {
        val found = parentRepository.findById(parent.id).getOrNull()

        return if (found == null) {
            parentRepository.save(parent)
            parent
        } else throw ResponseStatusException(PersonCreationError.CANNOT_CREATE_PERSON)
    }

    fun findAll(): List<Parent> = parentRepository.findAll().map { it }

    fun findMyChildren(parentId: Long): List<User> {
        val parent = parentRepository.findById(parentId).orElseThrow{NoSuchElementException("Parent not found with id $parentId")}
        val myChildren = parent.isFatherOf.map { it }
        return myChildren
    }
}



