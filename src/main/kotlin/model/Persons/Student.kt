package ar.org.schoolsync.model.Persons

import jakarta.persistence.*

@Entity
@Table(name = "app_student")
class Student(
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    var specificValue: Long,
    firstName: String,
    lastName: String,
    var absences: Int,
    ) : Person (firstName, lastName) {

        constructor(
            firstName: String,
            lastName: String,
        ) : this ( firstName, lastName, 0) {
            this.absences = 0
        }
    }
