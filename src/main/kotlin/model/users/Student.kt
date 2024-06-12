package ar.org.schoolsync.model.users

import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import jakarta.persistence.*

@Entity
class Student(
    firstName: String,
    lastName: String,
    email: String,
    password: String = ""
) : User(firstName, lastName, email, password) {
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    override var role = Role.STUDENT

    var absences: Int = 0
    fun addAbsence() {
        absences += 1
    }
}
