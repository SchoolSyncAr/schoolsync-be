package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import org.springframework.data.repository.CrudRepository


interface NotificationRepository: CrudRepository<Notification, Long>
