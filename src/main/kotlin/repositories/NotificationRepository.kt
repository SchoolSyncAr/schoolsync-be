package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.CrudRepository

interface NotificationRepository : CrudRepository<Notification, Long> {
    fun findAll(spec: Specification<Notification>): List<Notification>
    fun findDistinctByTitleAndContent(title: String, content: String): List<Notification>
}