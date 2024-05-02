package ar.org.schoolsync.Controllers

import ar.org.schoolsync.Domain.Student
import ar.org.schoolsync.Services.StudentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class StudentController (var studentService: StudentService) {

    @GetMapping("/students/all")
    @Operation(summary = "gets all the students")
    fun getAllStudents():List<Student>{
        return studentService.getAllStudents()
    }
}