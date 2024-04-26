package ar.org.schoolsync.services

import ar.org.schoolsync.domain.Notification
import ar.org.schoolsync.repositories.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService (val notificationRepository: NotificationRepository) {
    fun getAllNotifications(): List<Notification> {
        return notificationRepository.findAll().map { (it) }
    }

    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
        return notificationRepository.getAllCount()
    }

    fun createNotification(notification: Notification) {
        notificationRepository.save(notification)
    }

}