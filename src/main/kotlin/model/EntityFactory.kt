package ar.org.schoolsync.model

import ar.org.schoolsync.model.Persons.Parent
import ar.org.schoolsync.model.Persons.Person
import ar.org.schoolsync.model.Persons.Student
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class EntityFactory(private val encoder: PasswordEncoder) {

    fun createUser(type: Role) = when (type) {
        Role.USER -> NormalUser().build(encoder)
        Role.ADMIN -> AdminUser().build(encoder)
        Role.STUDENT -> TODO()
        Role.TEACHER -> TODO()
        Role.PARENT -> TODO()
    }


    fun createNotification(type: NotifScope) = when (type) {
        NotifScope.GENERAL -> GeneralNotification().build(encoder)
        NotifScope.INDIVIDUAL -> IndividualNotification().build(encoder)
    }

    fun createParent() = NormalParent().build(encoder)

    fun createStudent() = NormalStudent().build(encoder)

}

interface FactoryObject<T> {
    fun build(encoder: PasswordEncoder): T
}

class AdminUser : FactoryObject<User> {
    override fun build(encoder: PasswordEncoder) =
        User(
            firstName = "Admin",
            lastName = "User",
            email = "adminuser@schoolsync.mail.com",
            password = encoder.encode("adminuser"),
            ).apply { role = Role.ADMIN }
}

class NormalUser : FactoryObject<User> {
    override fun build(encoder: PasswordEncoder) =
        User(
            firstName = "Common",
            lastName = "User",
            email = "commonuser@schoolsync.mail.com",
            password = encoder.encode("commonuser"),
        )
}

class GeneralNotification : FactoryObject<Notification> {
    override fun build(encoder: PasswordEncoder): Notification =
        Notification(
            title = "General",
            content = "General",
            notificationReceiver = 0L,//mutableListOf(),
            notificationSender = 0L,
            notificationScope = NotifScope.GENERAL,
        )
}

class IndividualNotification : FactoryObject<Notification> {
    override fun build(encoder: PasswordEncoder): Notification =
        Notification(
            title = "General",
            content = "General",
            notificationReceiver = 0L, //mutableListOf(),
            notificationSender = 0L,
            notificationScope = NotifScope.GENERAL,
        )
}
class NormalParent : FactoryObject<Person> {
    override fun build(encoder: PasswordEncoder) =
        Parent(
            firstName = "Name",
            lastName = "Name",
            isFatherOf = mutableListOf(),
            notifications = mutableListOf(),
            notificationGroup = mutableListOf()
        )
}
class NormalStudent : FactoryObject<Person>{
    override fun build(encoder: PasswordEncoder) =
        Student(
            firstName = "Name",
            lastName = "Name",
            absences = 0,
            notifications = mutableListOf()
        )

}


