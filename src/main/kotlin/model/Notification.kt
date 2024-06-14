package ar.org.schoolsync.model

import ar.org.schoolsync.model.enums.NotificationWeight
import ar.org.schoolsync.model.users.User
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "notifications")
data class Notification(
    @ManyToOne
    var sender: User,

    @Column(length = 100, nullable = false)
    var title: String,

    @Column(length = 5000)
    var content: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var weight: NotificationWeight = NotificationWeight.BAJO,
) {
    val senderName = "${sender.firstName} ${sender.lastName}"

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var date: LocalDateTime = LocalDateTime.now()
}

