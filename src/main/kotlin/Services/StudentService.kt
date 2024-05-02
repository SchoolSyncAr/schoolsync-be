package ar.org.schoolsync.Services

import ar.org.schoolsync.Domain.Student
import ar.org.schoolsync.Repositories.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService (val studentRepository: StudentRepository){

    fun getAllStudents():List<Student>{
        return studentRepository.getAll()
    }
}