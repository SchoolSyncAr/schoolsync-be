//package ar.org.schoolsync.controllers
//
//import ar.org.schoolsync.domain.Notification
//import ar.org.schoolsync.services.NotificationService
//import io.swagger.v3.oas.annotations.Operation
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@CrossOrigin("*")
//class NotificationController (var notificationService: NotificationService) {
//
//    @GetMapping("/notifications/all")
//    fun getAllNotifications():List<ar.org.schoolsync.domain.Notification>{
//        return notificationService.getAllNotifications()
//    }
//
//    @GetMapping("/notifications/count")
//    fun getUnreadNotificationsCount():Int{
//        return notificationService.getUnreadNotificationsCount()
//    }
//
//    @PostMapping("/createNotifications")
//    @Operation(summary = "Creates a new notification")
//    fun crearNotificacion(@RequestBody notification: ar.org.schoolsync.domain.Notification) {
//        return notificationService.createNotification(notification)
//
//    }
//    @DeleteMapping("/deleteNotification/{notificationId}")
//    @Operation(summary = "Deletes a notification")
//    fun deleteNotification(@PathVariable notificationId: Int) : List<ar.org.schoolsync.domain.Notification>{
//        val notification = notificationService.deleteNotification(notificationId)
//        return notificationService.getAllNotifications()
//    }
//}