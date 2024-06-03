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

    var sender: Long,
    var scope: NotifScope,

    //@Enumerated(EnumType.STRING)
    @Column(length = 20)
    var weight: NotificationWeight = NotificationWeight.BAJO,

//    @ElementCollection(fetch = FetchType.EAGER)  //no funciona con LAZY
    var recipient: Long? = null, //MutableList<Long>? = null,

    @ElementCollection(fetch = FetchType.EAGER)
    var recipientGroups: MutableList<NotificationGroup> = mutableListOf(),

    var date: LocalDateTime = LocalDateTime.now(),
    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var read = false
    var pinned = false

    fun read() {
        read = !read
    }

    fun pin() {
        pinned = !pinned
    }
}

