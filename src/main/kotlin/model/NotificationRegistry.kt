package ar.org.schoolsync.model

import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.model.enums.NotificationWeight
import ar.org.schoolsync.model.users.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notification_registries")
class NotificationRegistry(
    @ManyToOne
    var sender: User,

    @ManyToOne
    var reciever: User,

    @ManyToOne
    var notification: Notification

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var read = false
    var pinned = false
    var date: LocalDateTime = LocalDateTime.now()

    fun read() {
        read = !read
    }

    fun pin() {
        pinned = !pinned
    }
}