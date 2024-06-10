package ar.org.schoolsync.model.users

import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import io.jsonwebtoken.security.Password
import jakarta.persistence.*

@Entity
class Parent(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
) : User(firstName, lastName, email, password) {
    override var role: Role = Role.PARENT

    @OneToMany(fetch = FetchType.EAGER)
    var isFatherOf: MutableList<User> = mutableListOf()

    @ElementCollection(fetch = FetchType.EAGER)
    var notificationGroups: MutableList<NotificationGroup> = mutableListOf(NotificationGroup.TODOS)
}