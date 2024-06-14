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
    open val firstName: String,

    @Column(length = 40, nullable = false)
    open val lastName: String,

    @Column(length = 60, nullable = false, unique = true)
    open val email: String,

    @Column(nullable = true)
    open var password: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    @ElementCollection(targetClass = NotificationGroup::class)
    @CollectionTable(
        name = "notification_groups",
        joinColumns = [JoinColumn(name = "parent_id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(
        name = "notification_group",
        length = 40
    )
    open val notificationGroups = mutableSetOf<NotificationGroup>()

    abstract var role: Role

    @Column(length = 20, nullable = false)
    open var status: Status = Status.ACTIVE

    open var created = LocalDateTime.now()

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



