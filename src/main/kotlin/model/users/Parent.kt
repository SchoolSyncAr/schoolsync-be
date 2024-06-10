package ar.org.schoolsync.model.users

import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import jakarta.persistence.*

@Entity
class Parent(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
) : User(firstName, lastName, email, password) {
    override var role: Role = Role.PARENT

    @ElementCollection(fetch = FetchType.EAGER)
    override var notificationGroups = mutableListOf(NotificationGroup.TODOS)

    @OneToMany(fetch = FetchType.EAGER)
    var isFatherOf: MutableList<User> = mutableListOf()
}