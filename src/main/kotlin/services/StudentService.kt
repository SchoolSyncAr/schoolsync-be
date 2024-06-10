package ar.org.schoolsync.services


import ar.org.schoolsync.exeptions.PersonCreationError
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.model.users.Student
import ar.org.schoolsync.repositories.StudentRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class StudentService (private val studentRepository: StudentRepository,
                      private val encoder: PasswordEncoder
                      ) {

    fun save(student: Student): Student {
        val found = studentRepository.findById(student.id).getOrNull()

        return if (found == null) {
            studentRepository.save(student)
            student
        } else throw ResponseStatusException(PersonCreationError.CANNOT_CREATE_PERSON)
    }

    fun findAll(): List<Student> = studentRepository.findAll().map { it }

}
