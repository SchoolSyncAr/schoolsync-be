package ar.org.schoolsync.controllers

import ar.org.schoolsync.domain.Notification
import ar.org.schoolsync.services.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Tag(name = "Notifications", description = "Notifications related operations")
class NotificationController(var notificationService: NotificationService) {

    @GetMapping("/notifications/all")
    fun getAllNotifications(): List<Notification> {
        return notificationService.getAllNotifications()
    }

    @GetMapping("/notifications/count")
    fun getUnreadNotificationsCount(): Int {
        return notificationService.getUnreadNotificationsCount()
    }

    @PostMapping("/create_notifications")
    @Operation(summary = "Creates a new notification")
    fun crearNotificacion(@RequestBody notification: Notification) {
        return notificationService.createNotification(notification)
    }
}