package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.User
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface NotificationRegistryRepository : CrudRepository<NotificationRegistry, Long> {
//    @Query(
//        """
//            SELECT NF
//            FROM NotificationRegistry AS NF
//            INNER JOIN NF.notification as N
//            WHERE LOWER(N.title) LIKE LOWER(CONCAT('%', :title, '%'))
//        """
//    )
//    fun findNotificationRegistriesByNotificationTitleOrderByVariable(
//        @Param("title")
//        title: String,
//        sort: Sort
//    ): List<NotificationRegistry>


    fun findNotificationRegistryByReceiverAndNotification(
        receiver: User,
        notification: Notification
    ): List<NotificationRegistry>


    @Query( """
        SELECT nf FROM NotificationRegistry as nf
        WHERE nf.receiver.id = :recieverId
    """ )
    fun findNotificationsByRecieverId(
        @Param("recieverId")
        receiverId: Long,
        spec: Specification<NotificationRegistry>,
        sort: Sort): List<NotificationRegistry>

    @Query( """ 
        SELECT nf FROM NotificationRegistry as nf 
        WHERE nf.receiver.id = :recieverId
    """ )
    fun findNotificationsByRecieverId(
        @Param("recieverId")
        receiverId: Long,
        sort: Sort): List<NotificationRegistry>
}