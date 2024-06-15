package ar.org.schoolsync.model

import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.model.enums.Status
import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
@Table(name = "users")
class User(
    @Column(length = 40, nullable = false)
    var firstName: String,

    @Column(length = 40, nullable = false)
    var lastName: String,

    @Column(length = 60, nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    // LIST TYPE ATTRIBUTES
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
    val notificationGroups: MutableSet<NotificationGroup> = mutableSetOf(NotificationGroup.TODOS)

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "students",
        joinColumns = [JoinColumn(name = "parent_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    val childrens: MutableSet<User> = mutableSetOf()

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER

    @Transient
    var userBehavior: UserBehaviorStrategy = RegularUserBehavior()

    @Column(length = 20, nullable = false)
    var status: Status = Status.ACTIVE
    var absences: Int = 0
    var created = LocalDateTime.now()
    var lastModified = created


    //FUNCTIONS
    fun addAbsence() {
        absences += 1
    }

    fun changeStatus(newStatus: Status) {
        status = newStatus
        registerUpdate()
    }

    fun addNotificationGroup(newNotificationGroup: NotificationGroup) {
        notificationGroups.add(newNotificationGroup)
        registerUpdate()
    }

    fun removeNotificationGroup(notificationGroup: NotificationGroup) {
        notificationGroups.remove(notificationGroup)
        registerUpdate()
    }

    fun addStudents(students: List<User>) {
        childrens.addAll(students)
        registerUpdate()
    }

    fun removeStudent(student: User) {
        childrens.remove(student)
        registerUpdate()
    }

    fun changeBehavior(behavior: UserBehaviorStrategy) {
        userBehavior = behavior
        userBehavior.set(this)
        registerUpdate()
    }

    fun registerUpdate() {
        lastModified = LocalDateTime.now()
    }
}


interface UserBehaviorStrategy {
    fun set(user: User)
}

class RegularUserBehavior : UserBehaviorStrategy {
    override fun set(user: User) {
        user.notificationGroups.clear()
        user.role = Role.USER
    }
}

class AdminBehavior : UserBehaviorStrategy {
    override fun set(user: User) {
        user.notificationGroups.add(NotificationGroup.PERSONAL)
        user.role = Role.ADMIN
    }
}

class ParentBehavior : UserBehaviorStrategy {
    override fun set(user: User) {
        user.notificationGroups.remove(NotificationGroup.PERSONAL)
        user.notificationGroups.add(NotificationGroup.PADRES)
        user.role = Role.PARENT
    }
}

class StudentBehavior : UserBehaviorStrategy {
    override fun set(user: User) {
        user.notificationGroups.remove(NotificationGroup.PERSONAL)
        user.notificationGroups.add(NotificationGroup.ESTUDIANTES)
        user.role = Role.STUDENT
    }
}
