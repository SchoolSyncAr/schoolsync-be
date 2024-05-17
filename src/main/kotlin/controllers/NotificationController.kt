package ar.org.schoolsync.controllers

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.services.NotificationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
class NotificationController (@Autowired var notificationService: NotificationService) {

    @GetMapping("/notifications/all")
    fun getAllNotifications(@RequestParam searchField: String?): List<Notification> {
        return notificationService.getAllNotifications(searchField)
    }
//
//    @GetMapping("/notifications/count")
//    fun getUnreadNotificationsCount():Int{
//        return notificationService.getUnreadNotificationsCount()
//    }
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
}