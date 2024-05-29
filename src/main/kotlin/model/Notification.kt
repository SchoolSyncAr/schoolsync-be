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

    var weight: NotificationWeight = NotificationWeight.BAJO,

    val date: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var scope: NotifScope = NotifScope.GENERAL,

    //@ElementCollection(fetch = FetchType.EAGER)  //no funciona con LAZY
    var recipient: Long? = null,//MutableList<Long> = mutableListOf(),

    @ElementCollection(fetch = FetchType.EAGER)
    var recipientGroups: MutableList<NotificationGroup> = mutableListOf()
    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    private var read = false

    fun read() {
        read = !read
    }

}

