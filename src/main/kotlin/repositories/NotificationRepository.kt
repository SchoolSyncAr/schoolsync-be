package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface NotificationRepository: CrudRepository<Notification, Long>{
    override fun findAll(): List<Notification>

    @Query("SELECT n FROM Notification n " +
            "WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%')) ")
    fun findNotificationsByTitleContainingIgnoreCaseOrderByVariable(
        @Param("title") title: String,
        sort: Sort
    ): List<Notification>

}
