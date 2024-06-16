package ar.org.schoolsync.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notification_registries")
class NotificationRegistry(
    @ManyToOne
    var receiver: User,

    @ManyToOne
    var notification: Notification
): CommonNotification() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var read = false
    var pinned = false
    var date: LocalDateTime = LocalDateTime.now()
    var weight = notification.weight

    fun read() {
        read = !read
    }

    fun pin() {
        pinned = !pinned
    }

    fun unifySendDate(newDate: LocalDateTime? = null) {
        newDate?.let { date = newDate } ?: run { date = notification.date }
    }
}