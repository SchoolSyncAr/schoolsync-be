package ar.org.schoolsync.model

import ar.org.schoolsync.dto.notification.NotificationDTO
import ar.org.schoolsync.model.enums.NotificationWeight
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "notifications")
data class Notification(
    @Column(length = 100, nullable = false)
    var title: String,

    @Column(length = 5000)
    var content: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var weight: NotificationWeight = NotificationWeight.BAJO,
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var date: LocalDateTime = LocalDateTime.now()
}

