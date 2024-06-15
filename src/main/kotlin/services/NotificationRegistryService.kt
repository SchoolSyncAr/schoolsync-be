package ar.org.schoolsync.services

import ar.org.schoolsync.dto.notification.*
import ar.org.schoolsync.exeptions.FindError
import ar.org.schoolsync.exeptions.ResponseFindException
import ar.org.schoolsync.model.*
import ar.org.schoolsync.model.enums.NotificationWeight
import ar.org.schoolsync.repositories.NotificationRegistryRepository
import ar.org.schoolsync.repositories.NotificationRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
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
            NotificationWeight.valueOf(data.weight)
        )
        notificationRepository.save(notification)

        if (data.recievers.isNotEmpty()) {
            data.recievers.forEach {
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

//    fun findAll(filter: SearchFilter): List<NotificationDTO> {
//        return notificationRegistryRepository
//            .findNotificationRegistriesByNotificationTitleOrderByVariable(
//                filter.searchField,
//                filter.getSort()
//            ).map { it.toDTO() }
//    }

    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
        return notificationRepository.findAll().count() // TODO: hacer correctamente en la db
    }

    fun deleteById(notificationId: Long) {
        return notificationRepository.deleteById(notificationId)
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
        val spec = SearchFilterBuilder(filter)
            .from()
            .title()
            .content()
            .build()

        spec?.let{
            return notificationRegistryRepository.findNotificationsByRecieverId(id, spec, filter.getSort()).map { it }
        }
        return notificationRegistryRepository.findNotificationsByRecieverId(id, filter.getSort()).map { it }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(notification: NotificationRegistry): NotificationDTO =
        notificationRegistryRepository.save(notification).toCreateResponse()
}