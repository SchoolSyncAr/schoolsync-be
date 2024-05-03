package ar.org.schoolsync.controllers

import ar.org.schoolsync.domain.Notification
import ar.org.schoolsync.services.NotificationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
class NotificationController (var notificationService: NotificationService) {

    @GetMapping("/notifications/all")
    fun getAllNotifications():List<Notification>{
        return notificationService.getAllNotifications()
    }

    @GetMapping("/notifications/count")
    fun getUnreadNotificationsCount():Int{
        return notificationService.getUnreadNotificationsCount()
    }

    @PostMapping("/createNotifications")
    @Operation(summary = "Creates a new notification")
    fun crearNotificacion(@RequestBody notification: Notification) {
        return notificationService.createNotification(notification)

    }

    @PutMapping("/unreadNotification")
    @Operation(summary = "Sets notification to read/unread")
    fun readNotification(@RequestParam notificationId: Int){
        return notificationService.readNotification(notificationId)
    }
}