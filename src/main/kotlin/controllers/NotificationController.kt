package ar.org.schoolsync.controllers

import ar.org.schoolsync.dto.notification.CreateNotificationDTO
import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.toCreateResponse
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.services.NotificationRegistryService
import ar.org.schoolsync.services.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.security.RolesAllowed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/notification")
@Tag(name = "Notification", description = "Notification Api Operations")
class NotificationController(
    @Autowired val notificationRegistryService: NotificationRegistryService,
    @Autowired val notificationService: NotificationService
) {

    @PostMapping("/create")
    @Operation(summary = "Crea una nueva notificación")
    fun create(@RequestBody notification: CreateNotificationDTO): NotificationDTO {
        return notificationRegistryService.create(notification).toCreateResponse()
    }

    @GetMapping("/all")
    @Operation(summary = "Retorna todas las notificaciones del sistema")
    fun findAll(
        @RequestParam searchField: String,
        @RequestParam orderParam: String,
        @RequestParam sortDirection: String
    ): List<NotificationDTO> =
        notificationService.findAll(SearchFilter(searchField, orderParam, sortDirection))

    @RolesAllowed("USER")
    @GetMapping("/count")
    fun getUnreadNotificationsCount(): Int {
        return notificationRegistryService.getUnreadNotificationsCount()
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/recipient-groups")
    @Operation(summary = "Devuelve los grupos de usuarios a quienes mandarles la Notif")
    fun getRecipientGroups(): List<String> {
        return NotificationGroup.entries.map { it.name }
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/{notificationId}/delete")
    @Operation(summary = "Elimina una notificación por ID")
    fun deleteById(@PathVariable notificationId: Long) {
        return notificationRegistryService.deleteById(notificationId)
    }

    @RolesAllowed("USER")
    @PutMapping("/read")
    @Operation(summary = "Sets notification status to read/unread")
    fun readNotification(@RequestBody notificationId: Long): NotificationDTO {
        return notificationRegistryService.readNotification(notificationId)
    }

    @RolesAllowed("USER")
    @PutMapping("/pin")
    @Operation(summary = "Sets notification status to pinned/unpinned")
    fun pinNotification(@RequestBody notificationId: Long): NotificationDTO {
        return notificationRegistryService.pinNotification(notificationId)
    }
}


//import ar.org.schoolsync.model.Notification
//import ar.org.schoolsync.services.NotificationService
//import io.swagger.v3.oas.annotations.Operation
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@CrossOrigin("*")
//class NotificationController (var notificationService: NotificationService) {
//
//    @GetMapping("/notifications/all")
//    fun getAllNotifications():List<ar.org.schoolsync.model.Notification>{
//        return notificationService.getAllNotifications()
//    }
//

//
//    @PostMapping("/createNotifications")
//    @Operation(summary = "Creates a new notification")
//    fun crearNotificacion(@RequestBody notification: ar.org.schoolsync.model.Notification) {
//        return notificationService.createNotification(notification)
//
//    }
//    @DeleteMapping("/deleteNotification/{notificationId}")
//    @Operation(summary = "Deletes a notification")
//    fun deleteNotification(@PathVariable notificationId: Int) : List<ar.org.schoolsync.model.Notification>{
//        val notification = notificationService.deleteNotification(notificationId)
//        return notificationService.getAllNotifications()
//    }
//
//    @PutMapping("/unreadNotification")
//    @Operation(summary = "Sets notification to read/unread")
//    fun readNotification(@RequestParam notificationId: Int){
//      return notificationService.readNotification(notificationId)
//    }
//}