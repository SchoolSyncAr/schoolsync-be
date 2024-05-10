package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Persons.Student
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface StudentRepository : CrudRepository<Student, UUID> {
}