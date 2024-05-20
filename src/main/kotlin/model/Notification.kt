package ar.org.schoolsync.model

import jakarta.persistence.*
import java.util.*
import ar.org.schoolsync.model.User


@Entity
@Table(name = "app_notif")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L,

    @Column(length = 100, nullable = false)
    var title: String,

    @Column(length = 1000)
    var content: String,

    @ElementCollection(fetch = FetchType.EAGER)  //no funciona con LAZY
    var notificationReceiver: MutableList<String>,

    var notificationSender: Long,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var notificationScope: NotScope,


    var weight: NotificationWeight = NotificationWeight.LOW) {

    private var read = false

    fun read() {
        read = !read
    }

    @ElementCollection(fetch = FetchType.EAGER)
    var notificationGroup: MutableList<NotificationGroup> = mutableListOf()

}

