package ar.org.schoolsync.controllers

import ar.org.schoolsync.model.Persons.Parent
import ar.org.schoolsync.model.Persons.Student
import ar.org.schoolsync.services.ParentService
import ar.org.schoolsync.services.StudentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/student")
@Tag(name = "Student", description = "Student Api Operations")

class StudentController (@Autowired val studentService: StudentService) {
    @PostMapping("/create")
    @Operation(summary = "Crea un nuevo student")
    fun create(@RequestBody student: Student): Student {
        println("CREANDOOOOOOOOOOOO $student")
        return studentService.save(student)
    }


}








