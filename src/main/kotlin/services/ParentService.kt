package ar.org.schoolsync.services


import ar.org.schoolsync.exeptions.PersonCreationError
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.Persons.Parent
import ar.org.schoolsync.repositories.ParentRepository
import ar.org.schoolsync.repositories.PersonRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ParentService (private val personRepository: PersonRepository,
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

}


