package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.users.User
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface NotificationRegistryRepository : CrudRepository<NotificationRegistry, Long> {
    @Query(
        """
            SELECT N 
            FROM Notification AS N 
            WHERE LOWER(N.title) LIKE LOWER(CONCAT('%', :title, '%'))
        """
    )
    fun findNotificationRegistriesByNotificationTitleOrderByVariable(
        @Param("title")
        title: String,
        sort: Sort
    ): List<NotificationRegistry>


    fun findNotificationRegistryBySenderAndRecieverAndNotification(
        sender: User,
        receiver: User,
        notification: Notification
    ): List<NotificationRegistry>
}