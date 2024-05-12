package ar.org.schoolsync.controllers

import ar.org.schoolsync.dto.notification.NotificationCreatedDTO
import ar.org.schoolsync.dto.notification.NotificationResponseDTO
import ar.org.schoolsync.dto.notification.toCreateDTO
import ar.org.schoolsync.dto.notification.toResponseDTO
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.Persons.Parent
import ar.org.schoolsync.services.NotificationService
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
}

