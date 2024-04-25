package ar.org.schoolsync.services

import ar.org.schoolsync.domain.Notification
import ar.org.schoolsync.repositories.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService (val notificationRepository: NotificationRepository) {

    fun getAllNotifications(): List<Notification> {
        return notificationRepository.findAll().map { (it) }
    }

    fun createNotification(notification: Notification) {
        notificationRepository.save(notification)
    }

}