package ar.org.schoolsync.Controllers

import ar.org.schoolsync.Domain.Parent
import ar.org.schoolsync.Domain.Student
import ar.org.schoolsync.Services.ParentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class ParentController (var parentService: ParentService){

    @GetMapping("/parents/getChildren/{parentId}")
    @Operation(summary = "gets all their children")
    fun getAllChildren(@PathVariable parentId: Int) : List<Student>{
        return parentService.getAllChildren(parentId)
    }
    @GetMapping("/parents/all")
    fun getAllParents(): List<Parent>{
        return parentService.getAllParents()
    }
}