package ar.org.schoolsync.services

import ar.org.schoolsync.dto.notification.CreateNotificationDTO
import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.toCreateResponse
import ar.org.schoolsync.exeptions.FindError
import ar.org.schoolsync.exeptions.ResponseFindException
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.model.SearchFilterBuilder
import ar.org.schoolsync.model.enums.NotificationPriorities
import ar.org.schoolsync.repositories.NotificationRegistryRepository
import ar.org.schoolsync.repositories.NotificationRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationRegistryService(
    @Autowired private val notificationRepository: NotificationRepository,
    @Autowired private val notificationRegistryRepository: NotificationRegistryRepository,
    @Autowired private val userService: UserService
) {
    fun findByID(id: Long): NotificationRegistry? = notificationRegistryRepository.findById(id).getOrNull()
    fun findOrErrorByID(id: Long): NotificationRegistry =
        findByID(id) ?: throw ResponseFindException(FindError.NOTIFICATION_NOT_FOUND(id))

    fun create(data: CreateNotificationDTO): Notification {
        val notification = Notification(
            userService.findOrErrorByID(data.sender),
            data.title,
            data.content,
            NotificationPriorities.valueOf(data.weight)
        )
        notificationRepository.save(notification)

        if (data.receivers.isNotEmpty()) {
            data.receivers.forEach {
                val notificationRegistry = NotificationRegistry(
                    userService.findOrErrorByID(it),
                    notification
                )
                notificationRegistryRepository.save(notificationRegistry)
            }
        }

        if (data.recipientGroups.isNotEmpty()) {
            data.recipientGroups.forEach {
                userService.allByGroup(it).forEach { user ->
                    val notificationRegistry = NotificationRegistry(
                        user,
                        notification
                    )
                    notificationRegistryRepository.save(notificationRegistry)
                }
            }
        }
        return notification
    }

    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
        return notificationRepository.findAll().count() // TODO: hacer correctamente en la db
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun readNotification(id: Long): NotificationDTO {
        val notification = findOrErrorByID(id)
        notification.read()
        return save(notification)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun pinNotification(id: Long): NotificationDTO {
        val notification = findOrErrorByID(id)
        notification.pin()
        return save(notification)
    }

    fun findAllByUserId(id: Long, filter: SearchFilter): List<NotificationRegistry> {
        val spec = SearchFilterBuilder(filter, true)
            .from()
            .title()
            .content()
            .userId(id)
            .active()
            .build()
        return notificationRegistryRepository.findAll(spec, filter.getSortUser()).map { it }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(notification: NotificationRegistry): NotificationDTO =
        notificationRegistryRepository.save(notification).toCreateResponse()
}
