package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Notification
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface NotificationRepository: CrudRepository<Notification, UUID>
