package ar.org.schoolsync.Controllers

import ar.org.schoolsync.Domain.Notification
import ar.org.schoolsync.Services.NotificationService
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
    @DeleteMapping("/deleteNotification/{notificationId}")
    @Operation(summary = "Deletes a notification")
    fun deleteNotification(@PathVariable notificationId: Int) : List<Notification>{
        val notification = notificationService.deleteNotification(notificationId)
        return notificationService.getAllNotifications()
    }
}