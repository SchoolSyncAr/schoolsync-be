package ar.org.schoolsync.services

import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.toAdminResponse
import ar.org.schoolsync.exceptions.FindError
import ar.org.schoolsync.exceptions.ResponseFindException
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.model.SearchFilterBuilder
import ar.org.schoolsync.model.enums.Status
import ar.org.schoolsync.repositories.NotificationRegistryRepository
import ar.org.schoolsync.repositories.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationService(
    @Autowired private val notificationRepository: NotificationRepository,
    @Autowired private val notificationRegistryRepository: NotificationRegistryRepository,
    @Autowired private val userService: UserService,
) {
    fun findByID(id: Long): Notification? = notificationRepository.findById(id).getOrNull()
    fun findOrErrorByID(id: Long): Notification =
        findByID(id) ?: throw ResponseFindException(FindError.NOTIFICATION_NOT_FOUND(id))

    fun findAll(filter: SearchFilter): List<NotificationDTO> {
        val spec = SearchFilterBuilder(filter)
            .from()
            .title()
            .content()
            .active()
            .build()

        return notificationRepository.findAll(spec, filter.getSortAdmin()).map { it.toAdminResponse() }
    }

    fun findAllByUser(filter: SearchFilter, unreadOnly: Boolean, parentId: Long, childrenId: Long?): List<NotificationDTO>{
        val spec = SearchFilterBuilder(filter)
            .from()
            .title()
            .content()
            .active()
            .build()

        var childrenIds = mutableListOf<Long>()
        childrenIds.add(parentId)
        childrenIds.addAll(userService.findMyChildren(parentId).map { it.id })
        if (childrenId != null){
            childrenIds = childrenIds.filter { it == childrenId }.toMutableList()
        }
        var notifications = notificationRegistryRepository.findAll().filter { notification ->
            childrenIds.any { it == notification.receiver.id }
        }
        if (unreadOnly) {
            notifications = notifications.filter { !it.read }
        }
        return notificationRepository.findAll(spec, filter.getSortAdmin())
            .filter { notification ->
                notifications.any { it.notification.id == notification.id }
            }
            .map { it.toAdminResponse() }
    }

    fun deleteById(id: Long) {
        notificationRepository.save(findOrErrorByID(id).apply {
            changeStatus(Status.DELETED)
        })
    }
}