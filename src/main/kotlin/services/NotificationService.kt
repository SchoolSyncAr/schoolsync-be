package ar.org.schoolsync.services


import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.dto.notification.NotificationResponseDTO
import ar.org.schoolsync.dto.notification.toDTO
import ar.org.schoolsync.dto.notification.toResponseDTO
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
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationService(private val notificationRepository: NotificationRepository,
                          private val parentRepository: ParentRepository,
                          private val studentRepository: StudentRepository,
                          private val encoder: PasswordEncoder) {

    fun create(data: NotificationDTO): Notification {
//        val found = notificationRepository.findById(notification.id).getOrNull()
        val notif = Notification(
            data.title,
            data.content,
            data.sender,
            NotifScope.valueOf(data.scope),
            NotificationWeight.valueOf(data.weight),
            date = LocalDateTime.now()
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

    fun findAll(filter: SearchFilter): List<NotificationDTO> {

        return if (filter.orderParam.isEmpty()){
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(filter.searchField,Sort.by("date").descending()).map { it.toDTO() }
        } else {
            val sortDirection = if (filter.sortDirection == "asc") Sort.Direction.ASC else Sort.Direction.DESC
            val sort = Sort.by(sortDirection, filter.orderParam)
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(filter.searchField, sort)
               .map { it.toDTO() }
        }
    }

    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
        return notificationRepository.findAll().count() // TODO: hacer correctamente en la db
    }

    fun addNotificationToList(notification: Notification) {
        if (notification.scope === NotifScope.GENERAL) {
            val allParents = parentRepository.findAll().map { it }

            allParents.forEach {parent ->
                val parentNotifGroups = parent.notificationGroups
                val targetNotifGroups = notification.recipientGroups
                val isAReceiver = (parentNotifGroups.any { parentGroup -> parentGroup in targetNotifGroups })
                if (isAReceiver) {
                    parent.notifications.add(notification)
                    parentRepository.save(parent)
                }
            }
        } else {
            val parent = parentRepository.findById(notification.recipient!!).get()
            parent.notifications.add(notification)
            parentRepository.save(parent)
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

        fun deleteById(notificationId: Long) {
            return notificationRepository.deleteById(notificationId)

        }

        fun readNotification(id: Long) {
            val notification = notificationRepository.findById(id)
                .orElseThrow { Businessexception("La Notificación con ID $id no fue encontrada") }
            notification.read()
        }
    }
