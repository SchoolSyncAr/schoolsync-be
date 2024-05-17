package ar.org.schoolsync.services

import ar.org.schoolsync.exeptions.Businessexception
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.repositories.NotificationRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationService(@Autowired val notificationRepository: NotificationRepository) {

    @Transactional
    fun getAllNotifications(searchField: String?): List<Notification> {
        return if (searchField.isNullOrEmpty()) {
            notificationRepository.findAll()
        } else {
            //recibis parámetros text del front que sea asc o dsc y construis de forma variable Sort.by("weight").descending() y el campo por el que sorteas
            notificationRepository.findNotificationsByTitleContainingIgnoreCaseOrderByVariable(searchField, Sort.by("weight").descending())
        }
    }


//    fun getUnreadNotificationsCount(/*idUsuario: Int*/): Int {
//        //Para luego devolver la cantidad de notificaciones no leídas de X usuario
//        //return notificationRepository.getUnreadNotificationsCount(idUsuario)
//        return notificationRepository.getAllCount()
//    }

    fun createNotification(notification: Notification) {
        notificationRepository.save(notification)

    }

    fun deleteNotification(id: Long) {
        return notificationRepository.deleteById(id)
    }

    fun readNotification(id: Long) {
        val notification = notificationRepository.findById(id).orElseThrow { Businessexception("La Notificación con ID $id no fue encontrada") }
        notification.read()
    }
}