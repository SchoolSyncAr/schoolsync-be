package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


interface NotificationRepository: CrudRepository<Notification, Long>{

    @Query("SELECT n FROM Notification n " +
            "WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :title, '%')) ")
    fun findNotificationsByTitleContainingIgnoreCaseOrderByVariable(
        @Param("title") title: String,
        sort: Sort
    ): List<Notification>

    override fun findAll(): List<Notification>

    //fun findAllByOrderByWeightDesc(): List<Notification>
}
