package ar.org.schoolsync.model

import ar.org.schoolsync.model.enums.NotificationPriorities
import ar.org.schoolsync.model.enums.Status
import jakarta.persistence.*
import java.time.LocalDateTime

sealed class CommonNotification
@Entity
@Table(name = "notifications")
data class Notification(
    @ManyToOne
    var sender: User,

    @Column(length = 100, nullable = false)
    var title: String,

    @Column(length = 5000)
    var content: String,

    //@Enumerated(EnumType.STRING)
    @Column(length = 20)
    var weight: NotificationPriorities = NotificationPriorities.BAJA,
): CommonNotification() {
    val senderName = "${sender.firstName} ${sender.lastName}"

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var date: LocalDateTime = LocalDateTime.now()

    @Column(length = 20, nullable = false)
    var status: Status = Status.ACTIVE
    var lastModified = date

    fun changeStatus(newStatus: Status) {
        status = newStatus
        registerUpdate()
    }

    fun registerUpdate() {
        lastModified = LocalDateTime.now()
    }
}

