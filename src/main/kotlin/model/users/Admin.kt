package ar.org.schoolsync.model.users

import ar.org.schoolsync.exeptions.Businessexception
import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import jakarta.persistence.*

@Entity
class Admin(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
) : User(firstName, lastName, email, password) {
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    override var role: Role = Role.ADMIN

    init {
        require(password.length > 7) { throw Businessexception("Password must be at least 8 characters") }
    }
}