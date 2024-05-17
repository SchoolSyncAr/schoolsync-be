package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


interface NotificationRepository: CrudRepository<Notification, Long>{

    //@Query(
    //    "SELECT DISTINCT n FROM Notification n " +
    //            "WHERE LOWER(n.content) LIKE LOWER(CONCAT('%', :searchField, '%')) " +
    //            "OR LOWER(n.title) LIKE LOWER(CONCAT('%', :searchField, '%'))"
    //)
    //fun findNotificationsByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(searchField: String): List<Notification>

    fun findNotificationsByTitleContainingIgnoreCase(title: String): List<Notification>


    override fun findAll(): List<Notification>
}
