package ar.org.schoolsync.services


import ar.org.schoolsync.exeptions.NotificationCreationError
import ar.org.schoolsync.exeptions.ResponseStatusException
import ar.org.schoolsync.exeptions.Businessexception
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.User
import ar.org.schoolsync.repositories.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.Base64.Encoder
import kotlin.jvm.optionals.getOrNull

@Service
class NotificationService(private val notificationRepository: NotificationRepository,
                          private val encoder: PasswordEncoder) {

    fun save(notification: Notification): Notification {
        val found = notificationRepository.findById(notification.id).getOrNull()

        return if (found == null) {
            val updated = notification.copy()
            notificationRepository.save(updated)
            updated
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

    fun createNotification(notification: Notification) {
        notificationRepository.save(notification)

    }

    fun deleteNotification(id: UUID) {
        return notificationRepository.deleteById(id)
    }

    fun readNotification(id: UUID) {
        val notification = notificationRepository.findById(id).orElseThrow { Businessexception("La Notificación con ID $id no fue encontrada") }
        notification.read()
    }
}