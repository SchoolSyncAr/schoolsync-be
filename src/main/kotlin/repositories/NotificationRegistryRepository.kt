package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.NotificationRegistry
import org.springframework.data.repository.CrudRepository

interface NotificationRegistryRepository: CrudRepository<NotificationRegistry, Long> {

}