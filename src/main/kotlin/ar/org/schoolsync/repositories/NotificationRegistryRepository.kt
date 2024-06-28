package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.CommonNotification
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.User
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface NotificationRegistryRepository : CrudRepository<NotificationRegistry, Long> {
    fun findNotificationRegistryByReceiverAndNotification(
        receiver: User,
        notification: Notification
    ): List<NotificationRegistry>

    fun findAll(spec: Specification<CommonNotification>, sort: Sort): List<NotificationRegistry>

    @Query("SELECT COUNT(n) FROM NotificationRegistry n WHERE n.receiver.id = :receiverId AND n.read = false")
    fun countUnreadNotificationsByReceiverId(@Param("receiverId") receiverId: Long): Int
}