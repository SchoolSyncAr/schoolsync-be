package ar.org.schoolsync.services

import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.toAdminDTO
import ar.org.schoolsync.dto.notification.toDTO
import ar.org.schoolsync.exeptions.FindError
import ar.org.schoolsync.exeptions.ResponseFindException
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.model.SearchFilterBuilder
import ar.org.schoolsync.repositories.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationService(
    @Autowired private val notificationRepository: NotificationRepository
) {
    fun findByID(id: Long): Notification? = notificationRepository.findById(id).getOrNull()
    fun findOrErrorByID(id: Long): Notification =
        findByID(id) ?: throw ResponseFindException(FindError.NOTIFICATION_NOT_FOUND(id))

    fun findAll(filter: SearchFilter): List<NotificationDTO> {
        val spec = SearchFilterBuilder(filter).from().title().content().build()

        spec?.let{
            return notificationRepository.findAll(spec).map { it.toAdminDTO() }
        }
        return notificationRepository.findAll().map { it.toAdminDTO() }
    }
}