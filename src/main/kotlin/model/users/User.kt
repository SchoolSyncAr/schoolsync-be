package ar.org.schoolsync.model.users

import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.model.enums.Status
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class User(
    @Column(length = 40, nullable = false)
    val firstName: String,

    @Column(length = 40, nullable = false)
    val lastName: String,

    @Column(length = 60, nullable = false, unique = true)
    val email: String,

    @Column(nullable = true, unique = true)
    var password: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    abstract var notificationGroups: MutableList<NotificationGroup>

    abstract var role: Role

    @Column(length = 20, nullable = false, unique = true)
    private var status: Status = Status.ACTIVE

    private var created = LocalDateTime.now()

    fun changeStatus ( newStatus: Status) {
        status = newStatus
    }

    fun addGroup (group: NotificationGroup) {
        notificationGroups.add(group)
    }

    fun removeGroup (group: NotificationGroup) {
        notificationGroups.remove(group)
    }
}



