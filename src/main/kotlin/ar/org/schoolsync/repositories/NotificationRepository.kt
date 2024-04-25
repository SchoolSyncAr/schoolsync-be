package ar.org.schoolsync.repositories

import ar.org.schoolsync.domain.Notification
import org.springframework.stereotype.Repository

@Repository
class NotificationRepository: CRepository<Notification>()