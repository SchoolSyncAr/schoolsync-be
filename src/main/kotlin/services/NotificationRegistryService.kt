package ar.org.schoolsync.services


import ar.org.schoolsync.dto.notification.CreateNotificationDTO
import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.toDTO
import ar.org.schoolsync.exeptions.FindError
import ar.org.schoolsync.exeptions.ResponseFindException
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.model.enums.NotificationWeight
import ar.org.schoolsync.repositories.NotificationRegistryRepository
import ar.org.schoolsync.repositories.NotificationRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationRegistryService(
    private val notificationRepository: NotificationRepository,
    private val notificationRegistryRepository: NotificationRegistryRepository,
    private val userService: UserService
) {
    fun findByID(id: Long): NotificationRegistry? = notificationRegistryRepository.findById(id).getOrNull()
    fun findOrErrorByID(id: Long): NotificationRegistry =
        findByID(id) ?: throw ResponseFindException(FindError.NOTIFICATION_NOT_FOUND(id))

    fun create(data: CreateNotificationDTO): Notification {
        val notification = Notification(
            data.title,
            data.content,
            NotificationWeight.valueOf(data.weight)
        )
        notificationRepository.save(notification)

        if (data.recievers.isNotEmpty()) {
            data.recievers.forEach {
                val notificationRegistry = NotificationRegistry(
                    userService.findOrErrorByID(data.sender),
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
                        userService.findOrErrorByID(data.sender),
                        user,
                        notification
                    )
                    notificationRegistryRepository.save(notificationRegistry)
                }
            }
        }

        return notification
    }

    fun findAll(filter: SearchFilter): List<NotificationDTO> {
        if (filter.orderParam.isNotEmpty()) {
            val sortDirection = filter.getDirection()
            val sort = Sort.by(
                Sort.Order(Sort.Direction.DESC, "pinned"),
                Sort.Order(sortDirection, filter.orderParam)
            )
            return notificationRegistryRepository
                .findNotificationRegistriesByNotificationTitleOrderByVariable(
                    filter.searchField,
                    sort
                ).map { it.toDTO() }
        }

        return notificationRegistryRepository
            .findNotificationRegistriesByNotificationTitleOrderByVariable(
                filter.searchField,
                Sort.by(
                    Sort.Order.desc("pinned"),
                    Sort.Order.asc("date")
                )
            ).map { it.toDTO() }
    }

    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
        return notificationRepository.findAll().count() // TODO: hacer correctamente en la db
    }

//        allStudents.forEach{
//            if(notification.notificationScope === NotScope.INDIVIDUAL)
//                allStudents.forEach {
//                    if (notification.notificationReceiver == it.id){
//                        it.notifications?.add(notification)
//                        studentRepository.save(it)
//                    }
//                }
//        }
//    }

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

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(notification: NotificationRegistry): NotificationDTO =
        notificationRegistryRepository.save(notification).toDTO()
}
