package ar.org.schoolsync.controllers

import ar.org.schoolsync.dto.parent.ParentDTO
import ar.org.schoolsync.dto.parent.toDTO
import ar.org.schoolsync.model.users.Parent
import ar.org.schoolsync.model.Persons.Person
import ar.org.schoolsync.services.ParentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/parent")
@Tag(name = "Parent", description = "Parent Api Operations")
class ParentController (@Autowired val parentService: ParentService) {

    @PostMapping("/create")
    @Operation(summary = "Crea un nuevo padre")
    fun create(@RequestBody parent: Parent): Parent {
        println("CREANDOOOOOOOOOOOO $parent")
        return parentService.save(parent)
    }

    @GetMapping("/all")
    @Operation(summary = "Retorna todos los padres del sistema")
    fun findAll(): List<ParentDTO> =
        parentService.findAll().map { it.toDTO() }

    @GetMapping("/myChildren/{parentId}")
    @Operation(summary = "devuelve todos los hijos de un determinado padre")
    fun findMyChildren(@PathVariable parentId: Long):List<Person>?{
        return parentService.findMyChildren(parentId)?.map { it }
    }
}

