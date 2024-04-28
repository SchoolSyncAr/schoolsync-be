package ar.org.schoolsync.Services

import ar.org.schoolsync.Domain.Notification
import ar.org.schoolsync.Repositories.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService (val notificationRepository: NotificationRepository) {

    fun getAllNotifications(): List<Notification> {
        return notificationRepository.getAll().map { (it) }
    }


    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
        return notificationRepository.getAllCount()
    }

    fun createNotification(notification: Notification) {
        notificationRepository.create(notification)

    }

    fun deleteNotification(notificationId: Int) {
        return notificationRepository.deleteById(notificationId)
    }
}