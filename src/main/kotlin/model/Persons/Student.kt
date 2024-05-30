package ar.org.schoolsync.model.Persons

import ar.org.schoolsync.model.Notification
import jakarta.persistence.*

@Entity
@Table(name = "app_student")
class Student(

    firstName: String,
    lastName: String,
    var absences: Int,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    var notifications: MutableList<Notification>? = null,
    ) : Person (firstName, lastName) {

        constructor(
            firstName: String,
            lastName: String,
            notifications: MutableList<Notification>?,
        ) : this ( firstName, lastName, 0, mutableListOf()) {
            this.absences = 0
            this.notifications = notifications

        }
    }
