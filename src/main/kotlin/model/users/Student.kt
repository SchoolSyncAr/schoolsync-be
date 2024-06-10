package ar.org.schoolsync.model.users

import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType

@Entity
class Student(
    firstName: String,
    lastName: String,
    email: String = "",
    password: String = ""
) : User(firstName, lastName, email, password) {
    override var role = Role.STUDENT

    @ElementCollection(fetch = FetchType.EAGER)
    override var notificationGroups = mutableListOf<NotificationGroup>()

    var absences: Int = 0
    fun addAbsence() {
        absences += 1
    }
}
