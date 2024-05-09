package ar.org.schoolsync.model

import jakarta.persistence.*
import java.util.*
import ar.org.schoolsync.model.User

@Entity
@Table(name = "app_notif")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID = UUID.randomUUID(),

    @Column(length = 100, nullable = false)
    var title: String,

    @Column(length = 1000)
    var content: String,

    @ElementCollection(fetch = FetchType.EAGER)  //no funciona con LAZY
    var notificationReceiver: MutableList<String>,

    var notificationSender: Int,    //UUID,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var notificationScope: NotScope


)








