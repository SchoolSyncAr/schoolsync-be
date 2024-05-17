package ar.org.schoolsync.services


import ar.org.schoolsync.exeptions.NotificationCreationError
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.exeptions.Businessexception
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.repositories.NotificationRepository
import ar.org.schoolsync.repositories.ParentRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationService(private val notificationRepository: NotificationRepository,
                          private val parentRepository: ParentRepository,
                          private val encoder: PasswordEncoder) {

    fun save(notification: Notification): Notification {
        val found = notificationRepository.findById(notification.id).getOrNull()
        return if (found == null) {
            notificationRepository.save(notification)
            notification
            addNotificationToList(notification)
            notification
        } else throw ResponseStatusException(NotificationCreationError.CANNOT_CREATE_NOTIFICATION)

    }

    fun findAll(): List<Notification> = notificationRepository.findAll().map { it }

//    fun getAllNotifications(): List<Notification> {
//        return notificationRepository.findAll().map { (it) }
//    }


//    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
//        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
//        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
//        return notificationRepository.getAllCount()
//    }

//    fun deleteNotification(id: UUID) {
//        return notificationRepository.deleteById(id)
//    }


    fun addNotificationToList(notification: Notification) {
        val allParents = parentRepository.findAll().map { it }

        allParents.forEach {

            val parentNotificationGroups = it.notificationGroup.map { it }
            val notificationGroups = notification.notificationGroup.map { it }
            val isAReceiver = (parentNotificationGroups.any { it in notificationGroups })
//                    || notificationGroups.any{it in parentNotificationGroups})
            if (isAReceiver) {
                it.notifications?.add(notification)
                parentRepository.save(it)
            } else {
                println("es individuaL")
            }
        }

        fun deleteNotification(id: Long) {
            return notificationRepository.deleteById(id)

        }

        fun readNotification(id: Long) {
            val notification = notificationRepository.findById(id)
                .orElseThrow { Businessexception("La Notificación con ID $id no fue encontrada") }
            notification.read()
        }
    }
}