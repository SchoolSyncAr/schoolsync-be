package ar.org.schoolsync.Controllers

import ar.org.schoolsync.Domain.Notification
import ar.org.schoolsync.Services.NotificationService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class NotificationController (var notificationService: NotificationService) {

    @GetMapping("/notifications/all")
    fun getAllNotifications():List<Notification>{
        return notificationService.getAllNotifications()
    }
}