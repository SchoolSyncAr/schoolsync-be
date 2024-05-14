package ar.org.schoolsync.model

import jakarta.persistence.*
import java.util.*
import ar.org.schoolsync.model.User

@Entity
@Table(name = "app_notif")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L,//UUID = UUID.randomUUID(),

    @Column(length = 100, nullable = false)
    var title: String,

    @Column(length = 1000)
    var content: String,

    @ElementCollection(fetch = FetchType.EAGER)  //no funciona con LAZY
    var notificationReceiver: MutableList<String>,

    var notificationSender: UUID,

//    @Enumerated(EnumType.STRING)
//    @Column(length = 20)
//    var notificationScope: NotScope

    @ElementCollection(fetch = FetchType.EAGER)
    var notificationGroup: MutableList<NotificationGroup>,


)
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    var id: UUID = UUID.randomUUID()
//}
//{
//    init {
//        if (id == null) {
//            id = UUID.randomUUID()
//        }
//    }
//}
//{
//    constructor(
//        title: String,
//        content: String,
//        notificationReceiver: MutableList<String>,
//        notificationSender: UUID,
//        notificationScope: NotScope
//        ) : this(
//        UUID.randomUUID(),
//        title,
//        content,
//        notificationReceiver,
//        notificationSender,
//        notificationScope
//        )
//}
