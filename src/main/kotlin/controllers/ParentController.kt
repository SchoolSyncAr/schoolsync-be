package ar.org.schoolsync.controllers

import ar.org.schoolsync.dto.parent.ParentNameOnlyDTO
import ar.org.schoolsync.dto.parent.toNameOnlyDTO
import ar.org.schoolsync.model.Persons.Parent
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
    fun findAll(): List<Parent> =
        parentService.findAll().map { it }

    @GetMapping("/names/all")
    @Operation(summary = "Retorna todos los nombres y ids de los padres del sistema")
    fun findAllNames(): List<ParentNameOnlyDTO> =
        parentService.findAll().map { it.toNameOnlyDTO() }

    @GetMapping("/myChildren/{parentId}")
    @Operation(summary = "devuelve todos los hijos de un determinado padre")
    fun findMyChildren(@PathVariable parentId: Long):List<Person>?{
        return parentService.findMyChildren(parentId)?.map { it }
    }
}

