package ar.org.schoolsync.services

import ar.org.schoolsync.dto.notification.*
import ar.org.schoolsync.exeptions.NotificationCreationError
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.exeptions.Businessexception
import ar.org.schoolsync.model.NotifScope
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationWeight
import ar.org.schoolsync.model.SearchFilter
import ar.org.schoolsync.repositories.NotificationRepository
import ar.org.schoolsync.repositories.ParentRepository
import ar.org.schoolsync.repositories.StudentRepository
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(private val notificationRepository: NotificationRepository,
                          private val parentRepository: ParentRepository,
                          private val parentService: ParentService,
                          private val studentRepository: StudentRepository,
                          private val encoder: PasswordEncoder) {

    fun create(data: NotificationDTO): Notification {
//        val found = notificationRepository.findById(data.id).getOrNull()
        val notif = Notification(
            data.title,
            data.content,
            data.sender,
            NotificationWeight.valueOf(data.weight),
            LocalDateTime.now()
        )

        if (data.scope == NotifScope.GENERAL.name) {
            notif.recipientGroups = data.recipientGroups
        } else {
            notif.recipient = data.recipient
        }

        notificationRepository.save(notif)
        addNotificationToList(notif)

        return notif
    }

    fun findAllforAdmin(filter: SearchFilter): List<AdminNotificationResponseDTO> {
        return if(filter.orderParam.isEmpty()){
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(filter.searchField, Sort.by("date").descending()).map {it.toAdminResponseDTO()}
        } else {
            val sortDirection = if (filter.sortDirection == "asc") Sort.Direction.ASC else Sort.Direction.DESC
            val sort = Sort.by(sortDirection, filter.orderParam)
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(filter.searchField, sort)
                .map { it.toAdminResponseDTO() }
        }
    }

    fun findAllforUser(filter: SearchFilter, userId: Long): List<UserNotificationResponseDTO> {
        return if(filter.orderParam.isEmpty()){
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(filter.searchField, Sort.by("date").descending()).map {it.toUserResponseDTO()}
        } else {
            val sortDirection = if (filter.sortDirection == "asc") Sort.Direction.ASC else Sort.Direction.DESC
            val sort = Sort.by(sortDirection, filter.orderParam)
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(filter.searchField, sort)
                .map { it.toUserResponseDTO() }
        }
    }

//    fun getAllNotifications(): List<Notification> {
//        return notificationRepository.findAll().map { (it) }
//    }

    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
        return notificationRepository.findAll().count() // TODO: hacer correctamente en la db
    }

//    fun deleteNotification(id: UUID) {
//        return notificationRepository.deleteById(id)
//    }

    fun addNotificationToList(notification: Notification) {
        if (notification.scope == NotifScope.GENERAL) {
            val allParents = parentService.findAll()
            allParents.forEach {
                val parentGroups = it.notificationGroup
                val targetGroups = notification.recipientGroups

                val isAReceiver = (parentGroups.any { group -> group in targetGroups })

                if (isAReceiver) {
                    it.notifications.add(notification)
                    parentRepository.save(it)
                }
            }
        } else {
            val parent = parentService.findByIdOrError(notification.recipient!!)
            parent.notifications.add(notification)
            parentRepository.save(parent)
//            allParents.forEach {
//                    if (notification.recipient == it.id) {
//                        it.notifications?.add(notification)
//                        parentRepository.save(it)
//                    }
//                }
            }
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

        fun deleteNotification(id: Long) {
            return notificationRepository.deleteById(id)

        }

        fun readNotification(id: Long) {
            val notification = notificationRepository.findById(id)
                .orElseThrow { Businessexception("La Notificación con ID $id no fue encontrada") }
            notification.read()
        }
    }
