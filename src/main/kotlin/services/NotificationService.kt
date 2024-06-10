package ar.org.schoolsync.services


import ar.org.schoolsync.dto.notification.CreateNotificationDTO
import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.toDTO
import ar.org.schoolsync.exeptions.Businessexception
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.enums.NotificationWeight
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.repositories.NotificationRegistryRepository
import ar.org.schoolsync.repositories.NotificationRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationRegistryRepository: NotificationRegistryRepository,
    private val userService: UserService
) {

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
        return if (filter.orderParam.isEmpty()) {
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(
                filter.searchField,
                Sort.by(
                    Sort.Order.desc("pinned"),
                    Sort.Order.asc("date")
                )
            ).map { it.toDTO() }
        } else {
            val sortDirection = if (filter.sortDirection == "asc") Sort.Direction.ASC else Sort.Direction.DESC
            val sort = Sort.by(
                Sort.Order(Sort.Direction.DESC, "pinned"),
                Sort.Order(sortDirection, filter.orderParam)
            )
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(filter.searchField, sort)
               .map { it.toDTO() }
        }
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

        fun readNotification(id: Long): NotificationDTO {
            val notification = notificationRepository.findById(id)
                .orElseThrow { Businessexception("La Notificación con ID $id no fue encontrada") }
            notification.read()
            notificationRepository.save(notification)
            println("Leyendo...")
            return notification.toDTO()
        }

        fun pinNotification(id: Long): NotificationDTO {
            val notification = notificationRepository.findById(id)
                .orElseThrow { Businessexception("La Notificación con ID $id no fue encontrada") }
            notification.pin()
            notificationRepository.save(notification)
            println("Pinneando...")
            return notification.toDTO()
        }
    }
