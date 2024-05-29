package ar.org.schoolsync.controllers

import ar.org.schoolsync.dto.notification.NotificationCreatedDTO
import ar.org.schoolsync.dto.user.*
import ar.org.schoolsync.dto.notification.*
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.services.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.security.RolesAllowed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("\${route.base}/notification")
@Tag(name = "Notification", description = "Notification Api Operations")
class NotificationController(@Autowired val notificationService: NotificationService) {

    @RolesAllowed("ADMIN")
    @PostMapping("/create")
    @Operation(summary = "Crea una nueva notificación")
    fun create(@RequestBody notification: Notification): NotificationCreatedDTO {
        println("CREANDOOOOOOOOOOOO $notification")
        return notificationService.save(notification).toCreateDTO()
    }

//    @PostMapping("/create/{senderUuid}/{receiverUuid}")
//    @Operation(summary = "Crea una nueva notificación")
//    fun create(@PathVariable senderUuid: UUID,
//               @PathVariable receiverUuid: UUID,
//               @RequestBody notification: Notification): NotificationCreatedDTO {
//        println("CREANDOOOOOOOOOOOO $notification")
//        return notificationService.save(notification).toCreateDTO()
//    }

    @RolesAllowed("ADMIN")
    @GetMapping("/all")
    @Operation(summary = "Retorna todas las notificaciones del sistema")
    fun findAll(@RequestParam searchField: String,
                @RequestParam orderParam: String,
                @RequestParam sortDirection: String): List<NotificationResponseDTO> =
        notificationService.findAll(SearchFilter(searchField,orderParam,sortDirection))

    @RolesAllowed("USER")
    @GetMapping("/count")
    fun getUnreadNotificationsCount(): Int {
        return notificationService.getUnreadNotificationsCount()
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/deleteNotification/{notificationId}")
    @Operation(summary = "Deletes a notification")
    fun deleteNotification(@PathVariable notificationId: Long,
                           @RequestParam searchField: String,
                           @RequestParam orderParam: String,
                           @RequestParam sortDirection: String
                           ) : List<NotificationResponseDTO> {
        val notification = notificationService.deleteNotification(notificationId)
        return notificationService.findAll(SearchFilter(searchField,orderParam,sortDirection))
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