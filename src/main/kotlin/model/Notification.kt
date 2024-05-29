package ar.org.schoolsync.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "app_notif")
data class Notification(
    @Column(length = 100, nullable = false)
    var title: String,
    @Column(length = 5000)
    var content: String,

//    @ElementCollection(fetch = FetchType.EAGER)  //no funciona con LAZY
    var notificationReceiver: Long, //MutableList<Long>? = null,

    var notificationSender: Long,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var notificationScope: NotifScope,
    var weight: NotificationWeight = NotificationWeight.BAJO,
    var date: LocalDateTime = LocalDateTime.now()
    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    private var read = false

    fun read() {
        read = !read
    }

    @ElementCollection(fetch = FetchType.EAGER)
    var notificationGroup: MutableList<NotificationGroup> = mutableListOf()

}

