package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.CommonNotification
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.User
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.CrudRepository

interface NotificationRegistryRepository : CrudRepository<NotificationRegistry, Long> {
    fun findNotificationRegistryByReceiverAndNotification(
        receiver: User,
        notification: Notification
    ): List<NotificationRegistry>

    fun findAll(spec: Specification<CommonNotification>, sort: Sort): List<NotificationRegistry>
}