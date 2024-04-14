package ar.org.schoolsync.Services

import ar.org.schoolsync.Domain.Notification
import ar.org.schoolsync.Repositories.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService (val notificationRepository: NotificationRepository) {

    fun getAllNotifications(): List<Notification> {
        return notificationRepository.getAll().map { (it) }
    }

}