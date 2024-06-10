package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.users.Student
import org.springframework.data.repository.CrudRepository

interface StudentRepository : CrudRepository<Student, Long> {
}